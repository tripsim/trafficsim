/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
var simulation = {}; // attach simulation to somewhere for navigation
var map = {};

simulation.displayResults = false;
simulation.defaultRefreshInterval = 500;
// {links : {linkId : feature}, lanes: {linkId:[features]}}
simulation.network = {
	links : {},
	nodes : {}
};
simulation.animation = false;
simulation.simulationName = null; // the simulation to request from server for
// animation
simulation.lastF = 0; // the last frame number requested
simulation.loadedF = 0; // the current last frame loaded from server
simulation.vehicles = {}; // key: vid value: vehicle
simulation.frames = {}; // key: fid values: {vid: record}
simulation.f = null; // current frame id
simulation.vehicle = function(vid, width, length) {
	this.vid = vid;
	this.width = width;
	this.length = length;
};
simulation.element = function(fid, vehicle, x, y, angle, color) {
	this.fid = fid;
	this.x = x;
	this.y = y;
	this.angle = angle;
	this.color = color;
	this.vehicle = vehicle;
};
simulation.refreshStrategy = null;

simulation.proj900913 = new OpenLayers.Projection('EPSG:900913');
simulation.proj4326 = new OpenLayers.Projection('EPSG:4326');

simulation.initMap = function() {

	var that = this;

	/***************************************************************************
	 * Base Map
	 **************************************************************************/
	map = this.map = new OpenLayers.Map('map', {
		projection : that.proj900913,
		allOverlays : true,
		fractionalZoom : true
	});
	var osm = new OpenLayers.Layer.OSM(null, null, {
		resolutions : [ 156543.03390625, 78271.516953125, 39135.7584765625,
				19567.87923828125, 9783.939619140625, 4891.9698095703125,
				2445.9849047851562, 1222.9924523925781, 611.4962261962891,
				305.74811309814453, 152.87405654907226, 76.43702827453613,
				38.218514137268066, 19.109257068634033, 9.554628534317017,
				4.777314267158508, 2.388657133579254, 1.194328566789627,
				0.5971642833948135, 0.25, 0.1, 0.05 ],
		serverResolutions : [ 156543.03390625, 78271.516953125,
				39135.7584765625, 19567.87923828125, 9783.939619140625,
				4891.9698095703125, 2445.9849047851562, 1222.9924523925781,
				611.4962261962891, 305.74811309814453, 152.87405654907226,
				76.43702827453613, 38.218514137268066, 19.109257068634033,
				9.554628534317017, 4.777314267158508, 2.388657133579254,
				1.194328566789627, 0.5971642833948135 ],
		transitionEffect : 'resize'
	});
	var gmap = new OpenLayers.Layer.Google('Google Streets', {
		numZoomLevels : 20,
		visibility : false
	});
	var gsat = new OpenLayers.Layer.Google('Google Satellite', {
		type : google.maps.MapTypeId.SATELLITE,
		numZoomLevels : 20,
		visibility : false
	});
	var ghyb = new OpenLayers.Layer.Google('Google Hybrid', {
		type : google.maps.MapTypeId.HYBRID,
		numZoomLevels : 21,
		visibility : false
	});
	map.addLayers([ osm, gmap, gsat, ghyb ]);

	/***************************************************************************
	 * Vector Layer, Vehicles
	 **************************************************************************/
	var vehicleTemplate = {
		strokeOpacity : 0.8,
		strokeWidth : '${strokeWidth}',
		strokeColor : '${strokeColor}',
		fillColor : '${getColor}' // using context.getColor(feature)
	};
	var vehicleContext = {
		strokeWidth : function(feature) {
			if (feature.attributes['trajectory'])
				return 3;
			return 0.1;
		},
		strokeColor : function(feature) {
			return feature.attributes['color'];
		},
		getColor : function(feature) {
			return feature.attributes['color'];
		}
	};
	this.refreshStrategy = new OpenLayers.Strategy.Refresh({
		interval : that.defaultRefreshInterval
	});
	var vehicleLayer = new OpenLayers.Layer.Vector('Vehicles', {
		projection : that.proj900913,
		styleMap : new OpenLayers.StyleMap(new OpenLayers.Style(
				vehicleTemplate, {
					context : vehicleContext
				})),
		rendererOptions : {
			zIndexing : true
		},
		strategies : [ that.refreshStrategy ],
		visibility : false
	});
	vehicleLayer.events.register('refresh', vehicleLayer, function() {
		if (that.f == that.lastF) {
			that.stopAnimation();
		} else if (that.loadedF - that.f < 50 && that.loadedF != that.lastF) {
			that.loadFrames(that.loadedF + 1);
		}

		delete that.frames[that.f - 1];
		var frame = that.frames[that.f++];
		var features = [];
		vehicleLayer.removeAllFeatures();
		for ( var i in frame) {
			var length = frame[i].vehicle.length;
			var width = frame[i].vehicle.width;
			var x = frame[i].x;
			var y = frame[i].y;
			var geom = new OpenLayers.Bounds(x - length, y - width / 2, x, y
					+ width / 2).toGeometry();
			var center = new OpenLayers.Geometry.Point(x, y);
			geom.rotate(frame[i].angle, center);
			var feature = new OpenLayers.Feature.Vector(geom, {
				color : frame[i].color
			});
			features.push(feature);
		}
		vehicleLayer.addFeatures(features);
	});
	this.drawTrajectories = function(trjs) {
		vehicleLayer.removeAllFeatures();
		var features = [];
		for ( var i in trjs) {
			var trj = trjs[i];
			var geom = OpenLayers.Geometry.fromWKT(trj);
			var feature = new OpenLayers.Feature.Vector(geom, {
				trajectory : true,
				color : 'red'
			});
			features.push(feature);
		}
		vehicleLayer.addFeatures(features);
		vehicleLayer.setVisibility(true);
		networkLayer.setVisibility(false);
		lanesLayer.setVisibility(false);
	};
	map.addLayer(vehicleLayer);

	/***************************************************************************
	 * Vector Layer, Network (Link-Node)
	 **************************************************************************/
	var networkTemplate = {
		pointRadius : 4,
		strokeWidth : 6,
		strokeColor : '${color}',
		strokeOpacity : 0.5,
		fillColor : 'black',
		fillOpacity : 0.5
	};
	var networkSelectTemplate = {
		pointRadius : 6,
		strokeColor : 'gray',
		strokeOpacity : 0.75,
		strokeWidth : 10,
		fillColor : 'gray',
		fillOpacity : 0.75
	};
	var networkContext = {
		color : function(feature) {
			return feature.attributes['linkId'] ? 'gray' : 'black';
		}
	};
	var networkLayer = new OpenLayers.Layer.Vector('Network', {
		projection : that.proj900913,
		styleMap : new OpenLayers.StyleMap({
			'default' : new OpenLayers.Style(networkTemplate, {
				context : networkContext
			}),
			'select' : new OpenLayers.Style(networkSelectTemplate, {
				context : networkContext
			})
		}),
		rendererOptions : {
			zIndexing : true
		}
	});
	var _drawLinks = function(links) {
		var features = [];
		for ( var i in links) {
			var geom = OpenLayers.Geometry.fromWKT(links[i]);
			var feature = new OpenLayers.Feature.Vector(geom, {
				linkId : i
			});
			features.push(feature);
			that.network.links[i] = feature;
		}
		networkLayer.addFeatures(features);
	};
	var _drawNodes = function(nodes) {
		var features = [];
		for ( var i in nodes) {
			var geom = OpenLayers.Geometry.fromWKT(nodes[i]);
			var feature = new OpenLayers.Feature.Vector(geom, {
				nodeId : i
			});
			features.push(feature);
			that.network.nodes[i] = feature;
		}
		networkLayer.addFeatures(features);
	};
	this.reDrawNetwork = function(links, nodes) {
		this.network.links = {};
		this.network.nodes = {};
		networkLayer.removeAllFeatures();
		_drawLinks(links);
		_drawNodes(nodes);
	};
	this.reDrawNetworkFeatures = function(links, nodes) {
		var linkIds = [], nodeIds = [];
		for ( var i in links)
			linkIds.push(i);
		for ( var i in nodes)
			nodeIds.push(i);
		this.removeNetworkFeatures(linkIds, nodeIds);
		_drawLinks(links);
		_drawNodes(nodes);
	};
	this.removeNetworkFeatures = function(linkIds, nodeIds) {
		// TODO make it find the exact lane feature for change
		var features = [];
		for ( var i in linkIds) {
			features = features.concat(networkLayer.getFeaturesByAttribute(
					'linkId', linkIds[i]));
			delete that.network.links[linkIds[i]];
		}
		for ( var i in nodeIds) {
			features = features.concat(networkLayer.getFeaturesByAttribute(
					'nodeId', nodeIds[i]));
			delete that.network.nodes[nodeIds[i]];
		}
		networkLayer.removeFeatures(features);
	};
	this.networkLayer = networkLayer;
	map.addLayer(networkLayer);

	/***************************************************************************
	 * Vector Layer, Lanes
	 **************************************************************************/
	var lanesTemplate = {
		strokeWidth : 8,
		strokeColor : '${color}',
		strokeOpacity : 0.8,
		fillColor : '${color}',
		fillOpacity : 0.8
	};
	var lanesSelectTemplate = {
		strokeWidth : 10,
		strokeColor : 'blue',
		strokeOpacity : 0.8,
		fillColor : 'blue',
		fillOpacity : 0.75
	};
	var lanesContext = {
		color : function(feature) {
			return feature.attributes['cid'] ? 'blue' : 'black';
		}
	};
	var lanesLayer = new OpenLayers.Layer.Vector('Lanes', {
		projection : that.proj900913,
		styleMap : new OpenLayers.StyleMap({
			'default' : new OpenLayers.Style(lanesTemplate, {
				context : lanesContext
			}),
			'select' : new OpenLayers.Style(lanesSelectTemplate, {
				context : lanesContext
			})
		}),
		rendererOptions : {
			zIndexing : true
		},
		visibility : false
	});
	var _drawLanes = function(lanes) {
		var features = [];
		for ( var linkId in lanes) {
			for ( var j in lanes[linkId]) {
				var geom = OpenLayers.Geometry.fromWKT(lanes[linkId][j]);
				var feature = new OpenLayers.Feature.Vector(geom, {
					linkId : linkId,
					laneId : j
				});
				features.push(feature);
			}
		}
		lanesLayer.addFeatures(features);
	};
	var _drawConnectors = function(connectors) {
		var features = [];
		for ( var cid in connectors) {
			var ids = cid.split('-');
			var geom = OpenLayers.Geometry.fromWKT(connectors[cid]);
			var feature = new OpenLayers.Feature.Vector(geom, {
				cid : cid,
				fromLink : ids[0],
				toLink : ids[2]
			});

			features.push(feature);
		}
		lanesLayer.addFeatures(features);
	};
	this.reDrawLanes = function(lanes, connectors) {
		for ( var linkId in lanes) {
			this.removeLaneFeatures(linkId);
		}
		_drawLanes(lanes);
		_drawConnectors(connectors);
	};
	this.reDrawAllLanes = function(lanes, connectors) {
		that.network.lanes = {};
		that.network.connectors = {};
		lanesLayer.removeAllFeatures();
		_drawLanes(lanes);
		_drawConnectors(connectors);
	};
	this.removeLaneFeatures = function(linkId) {
		// TODO make it find the exact lane feature for change
		var features = [];
		features = features.concat(lanesLayer.getFeaturesByAttribute('linkId',
				linkId));
		features = features.concat(lanesLayer.getFeaturesByAttribute(
				'fromLink', linkId));
		features = features.concat(lanesLayer.getFeaturesByAttribute('toLink',
				linkId));
		lanesLayer.removeFeatures(features);
	};
	this.removeConnector = function(cids) {
		for ( var i in cids) {
			var features = lanesLayer.getFeaturesByAttribute('cid', cids[i]);
			lanesLayer.removeFeatures(features);
		}
	};
	this.addConnectors = function(connectors) {
		_drawConnectors(connectors);
	};
	map.addLayer(lanesLayer);

	/***************************************************************************
	 * Box Control, Create Network Control (Select bounding box)
	 **************************************************************************/
	var selectAreaControl = new OpenLayers.Control();
	var boxHandler = null;
	var importAreaBounds = new OpenLayers.Bounds();
	OpenLayers.Util.extend(selectAreaControl, {
		draw : function() {
			// this Handler.Box will intercept the shift-mousedown
			// before Control.MouseDefault gets to see it
			this.box = boxHandler = new OpenLayers.Handler.Box(
					selectAreaControl, {
						"done" : this.notice
					}, {
					// keyMask : OpenLayers.Handler.MOD_SHIFT
					});
		},
		notice : function(bounds) {
			var ll = map.getLonLatFromPixel(new OpenLayers.Pixel(bounds.left,
					bounds.bottom));
			var ur = map.getLonLatFromPixel(new OpenLayers.Pixel(bounds.right,
					bounds.top));
			importAreaBounds.extend(ll);
			importAreaBounds.extend(ur);
			selectAreaControl.deactivate();
			boxHandler.deactivate();
			simwebhelper.getPanel('network/new', function() {
				jQuery('#map').css('cursor', 'auto');
			});
		}
	});
	map.addControl(selectAreaControl);

	/***************************************************************************
	 * Draw Control, Add Link
	 **************************************************************************/
	var editNetworkControl = new OpenLayers.Control.DrawFeature(networkLayer,
			OpenLayers.Handler.Path);
	var sketch = {
		start : null,
		end : null
	};
	editNetworkControl.events
			.register(
					'featureadded',
					networkLayer,
					function(f) {
						networkLayer.removeFeatures([ f.feature ]);
						if (f.feature.geometry.components.length < 2) {
							simwebhelper.feedback('needs at least two points.');
							return;
						}
						postData = {
							startNode : null,
							endNode : null,
							startLink : null,
							endLink : null,
							startCoordX : null,
							endCoordX : null,
							startCoordY : null,
							endCoordY : null,
							linearGeom : f.feature.geometry.toString()
						};
						if (sketch.start != null) {
							if (sketch.start.type == 'node') {
								postData.startNode = sketch.start.id;
							} else if (sketch.start.type == 'link') {
								postData.startLink = sketch.start.id;
								postData.startCoordX = sketch.start.x;
								postData.startCoordY = sketch.start.y;
							}
						}
						if (sketch.end != null) {
							if (sketch.end.type == 'node') {
								postData.endNode = sketch.end.id;
							} else if (sketch.end.type == 'link') {
								postData.endLink = sketch.end.id;
								postData.endCoordX = sketch.end.x;
								postData.endCoordY = sketch.end.y;
							}
						}
						if ((postData.startLink != null
								&& postData.endLink != null && postData.startLink == postData.endLink)
								|| (postData.startNode != null
										&& postData.endNode != null && postData.startNode == postData.endNode)) {
							simwebhelper
									.feedback('can not draw start and end at same feature.');
						} else {
							simwebhelper.action('network/draw', postData,
									function(data) {
										that.reDrawFeatures(data);
									});
						}
					});
	map.addControl(editNetworkControl);

	/***************************************************************************
	 * Select Control, Select Link
	 **************************************************************************/
	var selectLinkControl = new OpenLayers.Control.SelectFeature(networkLayer,
			{
				clickout : true
			});
	map.addControl(selectLinkControl);

	/***************************************************************************
	 * Select Control, Select Lane (lane connection configuration)
	 **************************************************************************/
	var selectLaneControl = new OpenLayers.Control.SelectFeature(lanesLayer, {
		clickout : true,
		multiple : true
	});
	map.addControl(selectLaneControl);

	/***************************************************************************
	 * LayerSitcher Control
	 **************************************************************************/
	map.addControl(new OpenLayers.Control.LayerSwitcher());

	/***************************************************************************
	 * Mouse Position Control, for developing only
	 **************************************************************************/
	var mp = new OpenLayers.Control.MousePosition({
		prefix : 'Coordinates: ',
		separator : ' | ',
		suffix : map.getProjectionObject()
	});
	// var mp = new OpenLayers.Control.MousePosition({
	// displayProjection : that.proj4326,
	// prefix : 'coordinates ',
	// separator : ' | ',
	// suffix : that.proj4326
	// });
	map.addControls([ mp ]);

	/***************************************************************************
	 * Events, Network Layer
	 **************************************************************************/
	var beginSketch = function() {
		networkLayer.events.remove('featureclick');
		networkLayer.events.remove('nofeatureclick');
		networkLayer.events
				.register(
						'featureclick',
						networkLayer,
						function(e) {
							if (sketch.start != null
									&& editNetworkControl.handler.line.geometry.components.length < 2) {
								return;
							}
							sketch.end = e.feature;
							if (e.feature.attributes['linkId']) {
								var geom = editNetworkControl.handler.line.geometry;
								sketch.endPoint = geom.components[geom.components.length - 1];
							} else {
								sketch.endPoint = new OpenLayers.Geometry.Point(
										e.feature.geometry.x,
										e.feature.geometry.y);
							}
							editNetworkControl.finishSketch();
						});
	};
	networkLayer.events
			.on({
				"featureselected" : function(e) {
					if (e.feature.attributes['linkId']) {
						that.linkClicked(e.feature.attributes['linkId']);
					} else if (e.feature.attributes['nodeId']) {
						that.nodeClicked(e.feature.attributes['nodeId']);
					}
				},
				"featureunselected" : function(e) {
					simwebhelper.hidePanel();
				},
				"featureover" : function(e) {
					e.feature.renderIntent = 'select';
					e.feature.layer.drawFeature(e.feature);
				},
				"featureout" : function(e) {
					e.feature.renderIntent = 'default';
					e.feature.layer.drawFeature(e.feature);
				},
				"sketchstarted" : function() {
					sketch.start = sketch.end = sketch.endPoint = null;
					networkLayer.events.register('featureclick', networkLayer,
							function(e) {
								sketch.start = e.feature;
								beginSketch();
							});
					networkLayer.events.register('nofeatureclick',
							networkLayer, function() {
								beginSketch();
							});
				},
				"sketchcomplete" : function(e) {
					networkLayer.events.remove('featureclick');
					var drawGeom = e.feature.geometry;
					if (sketch.start != null) {
						var obj = {};
						if (sketch.start.attributes['nodeId']) {
							drawGeom.components[0].x = sketch.start.geometry.x;
							drawGeom.components[0].y = sketch.start.geometry.y;
							obj.type = 'node';
							obj.id = sketch.start.attributes['nodeId'];
							sketch.start = obj;
						} else {
							obj.type = 'link';
							obj.id = sketch.start.attributes['linkId'];
							obj.x = drawGeom.components[0].x;
							obj.y = drawGeom.components[0].y;
							sketch.start = obj;
						}
					}
					if (sketch.end != null) {
						var obj = {};
						if (drawGeom.components > 2)
							drawGeom.components[drawGeom.components.length] = sketch.endPoint;
						if (sketch.end.attributes['nodeId']) {
							obj.type = 'node';
							obj.id = sketch.end.attributes['nodeId'];
							sketch.end = obj;
						} else {
							obj.type = 'link';
							obj.id = sketch.end.attributes['linkId'];
							obj.x = sketch.endPoint.x;
							obj.y = sketch.endPoint.y;
							sketch.end = obj;
						}
					}
				}
			});

	/***************************************************************************
	 * Events, Lanes Layer
	 **************************************************************************/
	lanesLayer.events
			.on({
				"featureselected" : function(e) {
					if (e.feature.attributes['cid']) {
						if (lanesLayer.selectedFeatures.length === 2) {
							selectLaneControl
									.unselect(lanesLayer.selectedFeatures[0]);
						}
						var ids = e.feature.attributes['cid'].split('-');
						simwebhelper.getPanel('connector/view/' + ids[0]
								+ ';laneId=' + ids[1] + '/' + ids[2]
								+ ';laneId=' + ids[3]);
						return;
					}

					if (lanesLayer.selectedFeatures.length === 2) {
						if (lanesLayer.selectedFeatures[0].attributes['cid']) {
							selectLaneControl
									.unselect(lanesLayer.selectedFeatures[0]);
							simwebhelper.hidePanel();
							return;
						}
						var link1 = lanesLayer.selectedFeatures[0].attributes['linkId'];
						var lane1 = lanesLayer.selectedFeatures[0].attributes['laneId'];
						var link2 = lanesLayer.selectedFeatures[1].attributes['linkId'];
						var lane2 = lanesLayer.selectedFeatures[1].attributes['laneId'];
						if (link1 === link2) {
							simwebhelper
									.error('Cannot connect lanes on the same link.');
						} else {
							var postData = {
								link1 : link1,
								link2 : link2,
								lane1 : lane1,
								lane2 : lane2
							};
							simwebhelper.action('connector/add', postData,
									function(connectors) {
										that.addConnectors(connectors);
									});
						}
						selectLaneControl.unselectAll();
					}
				},
				"featureunselected" : function(e) {
					simwebhelper.hidePanel();
				},
				"featureover" : function(e) {
					e.feature.renderIntent = 'select';
					e.feature.layer.drawFeature(e.feature);
				},
				"featureout" : function(e) {
					if (lanesLayer.selectedFeatures.indexOf(e.feature) > -1)
						return;
					e.feature.renderIntent = 'default';
					e.feature.layer.drawFeature(e.feature);
				}
			});
	/***************************************************************************
	 * Utility Functions
	 **************************************************************************/
	/* remove all features on map */
	this.clearLayers = function() {
		networkLayer.removeAllFeatures();
		lanesLayer.removeAllFeatures();
	};
	/* redraw features */
	this.reDrawFeatures = function(data) {
		if (data['links'] != null || data['nodes'] != null)
			that.reDrawNetworkFeatures(data['links'], data['nodes']);
		if (data['lanes'] != null || data['connectors'] != null)
			that.reDrawLanes(data['lanes'], data['connectors']);
	};
	/* redraw all features */
	this.reDrawAllFeatures = function(data) {
		if (data['links'] != null || data['nodes'] != null)
			simulation.reDrawNetwork(data['links'], data['nodes']);
		if (data['lanes'] != null || data['connectors'] != null)
			simulation.reDrawAllLanes(data['lanes'], data['connectors']);
	};
	/* select area */
	this.prepareSelectArea = function() {
		simulation.clearLayers();
		selectAreaControl.activate();
		boxHandler.activate();
	};
	/* generate network from import */
	this.createNetworkFromOsm = function(url, highway) {
		var postData = {
			bbox : importAreaBounds.transform(map.getProjectionObject(),
					that.proj4326).toBBOX(),
			highway : highway,
			url : url
		};
		simwebhelper.action('network/create', postData, function(data) {
			that.reDrawNetwork(data['links'], data['nodes']);
			importAreaBounds = new OpenLayers.Bounds();
			that.editLinks();
		});
	};
	/* activate link draw */
	this.drawLinks = function() {
		networkLayer.setVisibility(true);
		lanesLayer.setVisibility(false);
		vehicleLayer.setVisibility(false);
		editNetworkControl.activate();
		selectLinkControl.deactivate();
		selectLaneControl.deactivate();
	};
	/* activate link selection */
	this.editLinks = function() {
		networkLayer.setVisibility(true);
		lanesLayer.setVisibility(false);
		vehicleLayer.setVisibility(false);
		editNetworkControl.deactivate();
		selectLinkControl.activate();
		selectLaneControl.deactivate();
	};
	/* activate lane selection */
	this.editLanes = function() {
		networkLayer.setVisibility(false);
		lanesLayer.setVisibility(true);
		vehicleLayer.setVisibility(false);
		editNetworkControl.deactivate();
		selectLinkControl.deactivate();
		selectLaneControl.activate();
	};
	/* link node click action */
	this.linkClicked = function(linkId) {
		if (this.displayResults) {
			this.showLinkTsd(linkId);
		} else {
			simwebhelper.getPanel('link/view/' + linkId);
		}

	};
	this.nodeClicked = function(nodeId) {
		if (this.displayResults) {
			this.showNodeTrjs(nodeId);
		} else {
			simwebhelper.getPanel('node/view/' + nodeId);
		}
	}
	this.showNodeTrjs = function(nodeId) {
		var params = this.getResultParams();
		params.nodeId = nodeId;
		simwebhelper.getStrWithParams('results/trajectories/'
				+ params.simulationName, params, function(trjs) {
			if (trjs.trajectories) {
				simulation.drawTrajectories(trjs.trajectories);
			}
		});
	}
	this.showLinkTsd = function(linkId) {
		var params = this.getResultParams();
		params.linkId = linkId;
		simwebhelper.getStrWithParams('results/tsd/' + params.simulationName,
				params, function(data) {
					if (data.serieses) {
						simplot.plot(data.serieses);
					}
				});
	}
	/* results */
	this.displayResult = function(state) {
		if (state === true) {
			this.displayResults = true;
		} else {
			this.displayResults = false;
		}
	}
	/* load all frames */
	this.loadFrames = function(start) {
		jQuery.get('results/frames/' + that.simulationName, {
			offset : start
		}, function(data) {
			if (data.startFrame == data.endFrame) {
				that.lastF = that.loadedF;
				return;
			}
			for ( var i in data['vehicles']) {
				var vs = data.vehicles[i].split(',');
				var v = new that.vehicle(vs[0], vs[1], vs[2]);
				that.vehicles[v.vid] = v;
			}
			for ( var i in data['elements']) {
				var es = data.elements[i].split(',');
				var v = that.vehicles[es[1]];
				if (!v)
					continue;
				var e = new that.element(es[0], v, es[2], es[3], es[4], es[5]);
				var frame = that.frames[es[0]];
				if (!frame) {
					that.frames[es[0]] = frame = [];
				}
				frame.push(e);
			}
			that.loadedF = data.endFrame;
		});
	};
	/* clear frames */
	this.clearFrames = function() {
		that.simulationName = null;
		that.lastF = 0;
		that.loadedF = 0;
		that.f = 0;
		that.frames = {};
		that.vehicles = {};
	};
	/* start animation */
	this.startAnimation = function() {
		if (this.animation || !this.displayResults) {
			return;
		}
		this.clearFrames();
		var params = this.getResultParams();

		this.simulationName = params.simulationName;
		this.f = params.offset;
		this.lastF = params.limit + params.offset;
		that.loadFrames(this.f);

		vehicleLayer.setVisibility(true);
		that.refreshStrategy.activate();
	};
	/* stop animation */
	this.stopAnimation = function() {
		vehicleLayer.setVisibility(false);
		this.clearFrames();
		this.refreshStrategy.deactivate();
	};
};
