var simulation = {}; // attach simulation to somewhere for navigation
var map = {};

simulation.defaultRefreshInterval = 400;

simulation.network = {}; // {links : {linkId : feature}, lanes: {linkId:
// [features]}}
simulation.vehicles = {}; // key: vid value: vehicle
simulation.frames = {}; // key: fid values: {vid: record}
simulation.totalF = 0;
simulation.f = 0; // current frame id
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
		strokeWidth : 0.1,
		strokeColor : '${strokeColor}',
		fillColor : '${getColor}' // using context.getColor(feature)
	};
	var vehicleContext = {
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
		if (that.totalF == that.f) {
			that.refreshStrategy.deactivate();
		}
		var frame = that.frames[that.f++];
		var features = [];
		vehicleLayer.removeAllFeatures();
		for ( var i in frame) {
			var length = frame[i].vehicle.length;
			var width = frame[i].vehicle.width;
			var x = frame[i].x;
			var y = frame[i].y;
			var geom = new OpenLayers.Bounds(x - length / 2, y - width / 2, x
					+ length / 2, y + width / 2).toGeometry();
			var center = new OpenLayers.Geometry.Point(x, y);
			geom.rotate(frame[i].angle, center);
			var feature = new OpenLayers.Feature.Vector(geom, {
				color : frame[i].color
			});
			features.push(feature);
		}
		vehicleLayer.addFeatures(features);
	});
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
		fillOpacity : 0.75,
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
			}),
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
	this.networkLayer = networkLayer;
	map.addLayer(networkLayer);

	/***************************************************************************
	 * Vector Layer, Lanes
	 **************************************************************************/
	var lanesTemplate = {
		strokeWidth : 6,
		strokeColor : '${color}',
		strokeOpacity : 0.8,
		fillColor : '${color}',
		fillOpacity : 0.8
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
			// TODO make it find the exact lane feature for change
			var features = lanesLayer.getFeaturesByAttribute('linkId', linkId);
			lanesLayer.removeFeatures(features);
			var features = lanesLayer
					.getFeaturesByAttribute('fromLink', linkId);
			lanesLayer.removeFeatures(features);
			var features = lanesLayer.getFeaturesByAttribute('toLink', linkId);
			lanesLayer.removeFeatures(features);
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
	 * Draw Control, Create Network Control
	 **************************************************************************/
	// new OpenLayers.Control.DrawFeature(pointLayer, OpenLayers.Handler.Point)
	// new OpenLayers.Control.DrawFeature(lineLayer, OpenLayers.Handler.Path)
	var selectAreaControl = new OpenLayers.Control.DrawFeature(networkLayer,
			OpenLayers.Handler.RegularPolygon, {
				handlerOptions : {
					sides : 4,
					irregular : true
				}
			});
	selectAreaControl.events.register('featureadded', networkLayer,
			function(f) {
				simulation.importArea = f.feature.geometry.bounds;
				selectAreaControl.deactivate();
				simwebhelper.getPanel('view/network-new', function() {
					jQuery('#map').css('cursor', 'auto');
				});
			});
	map.addControl(selectAreaControl);

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
	var mp900913 = new OpenLayers.Control.MousePosition({
		div : $('proj-900913'),
		prefix : 'Coordinates: ',
		suffix : ' (in ' + map.getProjectionObject() + ')'
	});
	var mp4326 = new OpenLayers.Control.MousePosition({
		div : $('proj-4326'),
		displayProjection : that.proj4326,
		prefix : 'Coordinates: ',
		suffix : ' (in ' + that.proj4326 + ')'
	});
	map.addControls([ mp900913, mp4326 ]);

	/***************************************************************************
	 * Events, Network Layer
	 **************************************************************************/
	networkLayer.events.on({
		"featureselected" : function(e) {
			if (e.feature.attributes['linkId'])
				simwebhelper.getPanel('view/link/'
						+ e.feature.attributes['linkId']);
			else if (e.feature.attributes['nodeId'])
				simwebhelper.getPanel('view/node/'
						+ e.feature.attributes['nodeId']);

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
						simwebhelper.getPanel('view/connector/' + ids[0]
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
							simwebhelper.action('action/connectlanes',
									postData, function(connectors) {
										that.addConnectors(connectors);
									});
						}
						selectLaneControl.unselectAll();
					}
				},
				"featureunselected" : function(e) {
					simwebhelper.hidePanel();
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
	/* select area */
	this.prepareSelectArea = function() {
		selectAreaControl.activate();
	};
	/* generate network from import */
	this.createNetworkFromOsm = function(highway) {
		var postData = {
			bbox : simulation.importArea.transform(map.getProjectionObject(),
					that.proj4326).toBBOX(),
			highway : highway
		};
		simwebhelper.action('createnetwork', postData, function(data) {
			that.reDrawNetwork(data['links'], data['nodes']);
			that.editLinks();
		});
	};
	/* activate link selection */
	this.editLinks = function() {
		selectLinkControl.activate();
		selectLaneControl.deactivate();
		networkLayer.setVisibility(true);
		lanesLayer.setVisibility(false);
	};
	/* activate lane selection */
	this.editLanes = function() {
		selectLinkControl.deactivate();
		selectLaneControl.activate();
		networkLayer.setVisibility(false);
		lanesLayer.setVisibility(true);
	};

};

simulation.load = function() {
	var that = this;
	that.totalF = 0;
	that.vehicles = {};
	that.frames = {};
	jQuery.get('loaddemo', function(data) {
		var obj = eval('(' + data + ')');
		for ( var i in obj['vehicles']) {
			var vs = obj.vehicles[i].split(',');
			var v = new that.vehicle(vs[0], vs[1], vs[2]);
			that.vehicles[v.vid] = v;
		}
		for ( var i in obj['elements']) {
			var es = obj.elements[i].split(',');
			var v = that.vehicles[es[1]];
			if (!v)
				continue;
			var e = new that.element(es[0], v, es[2], es[3], es[4], es[5]);
			var frame = that.frames[es[0]];
			if (!frame) {
				that.frames[es[0]] = frame = [];
				that.totalF++;
			}
			frame.push(e);
		}

		simulation.animate();
	});
};

simulation.animate = function() {
	this.f = 0;
	this.refreshStrategy.activate();
};
