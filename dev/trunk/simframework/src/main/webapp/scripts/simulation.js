var simulation = {}; // attach simulation to somewhere for navigation
simulation.defaultCenterX = -9952964.247717002;
simulation.defaultCenterY = 5323065.604899759;
simulation.defaultRefreshInterval = 400;

simulation.map = {};
simulation.network = {}; // links : [], nodes: [], connectors: []
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

simulation.initMap = function() {
	var proj900913 = new OpenLayers.Projection('EPSG:900913');
	var proj4326 = new OpenLayers.Projection('EPSG:4326');

	// load();
	var that = this;

	var map = this.map = new OpenLayers.Map('map', {
		projection : proj900913,
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
//	var gmap = new OpenLayers.Layer.Google('Google Streets', {
//		numZoomLevels : 20,
//		visibility : false
//	});
//	var gsat = new OpenLayers.Layer.Google("Google Satellite", {
//		type : google.maps.MapTypeId.SATELLITE,
//		numZoomLevels : 20,
//		visibility : false
//	});
//	var ghyb = new OpenLayers.Layer.Google("Google Hybrid", {
//		type : google.maps.MapTypeId.HYBRID,
//		numZoomLevels : 21,
//		visibility : false
//	});
//	map.addLayers([ osm, gmap, gsat, ghyb ]);
	map.addLayers([ osm ]);

	var mp900913 = new OpenLayers.Control.MousePosition({
		div : $('proj-900913'),
		prefix : 'Coordinates: ',
		suffix : ' (in ' + map.getProjectionObject() + ')'
	});
	var mp4326 = new OpenLayers.Control.MousePosition({
		div : $('proj-4326'),
		displayProjection : proj4326,
		prefix : 'Coordinates: ',
		suffix : ' (in ' + proj4326 + ')'
	});
	map.addControls([ mp900913, mp4326 ]);

	var center = new OpenLayers.LonLat(this.defaultCenterX, this.defaultCenterY);
	map.setCenter(center, map.numZoomLevels);

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
		projection : proj900913,
		styleMap : new OpenLayers.StyleMap(new OpenLayers.Style(
				vehicleTemplate, {
					context : vehicleContext
				})),
		rendererOptions : {
			zIndexing : true
		},
		strategies : [ that.refreshStrategy ]
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

	var networkTemplate = {
		strokeColor : '${strokeColor}',
		strokeOpacity : 0.5,
		fillColor : 'black',
		fillOpacity : 0.5
	};
	var networkContext = {
		strokeColor : function(feature) {
			return 'black';
		}
	};
	var networkLayer = new OpenLayers.Layer.Vector('Network', {
		projection : proj900913,
		styleMap : new OpenLayers.StyleMap(new OpenLayers.Style(
				networkTemplate, {
					context : networkContext
				})),
		rendererOptions : {
			zIndexing : true
		}
	});
	// draw network TODO make it a function
	this.reDrawNetwork = function() {
		networkLayer.removeAllFeatures();
		var features = [];
		for ( var i in this.network['links']) {
			var geom = OpenLayers.Geometry.fromWKT(this.network['links'][i]);
			var feature = new OpenLayers.Feature.Vector(geom);
			features.push(feature);
		}
		networkLayer.addFeatures(features);
	};
	this.networkLayer = networkLayer;
	map.addLayer(networkLayer);

	// new OpenLayers.Control.DrawFeature(pointLayer, OpenLayers.Handler.Point)
	// new OpenLayers.Control.DrawFeature(lineLayer, OpenLayers.Handler.Path)
	var drawControl = new OpenLayers.Control.DrawFeature(networkLayer,
			OpenLayers.Handler.RegularPolygon, {
				handlerOptions : {
					sides : 4,
					irregular : true
				}
			});
	drawControl.events
			.register(
					'featureadded',
					networkLayer,
					function(f) {
						// TODO change the name, and design an object to hold it
						simulation.area = f.feature.geometry.bounds;
						drawControl.deactivate();
						OpenLayers.Request
								.GET({
									url : 'resources/components/network-new.html',
									callback : function(request) {
										// TODO make it a function and need a
										// framework
										document
												.getElementById("user-configuration").style.display = 'inline';
										document
												.getElementById("user-configuration").innerHTML = request.responseText;
									}
								});
					});
	this.drawControl = drawControl;
	map.addControl(drawControl);

	map.addControl(new OpenLayers.Control.LayerSwitcher());
};

simulation.load = function() {
	var that = this;
	that.totalF = 0;
	that.vehicles = {};
	that.frames = {};
	OpenLayers.Request.GET({
		url : 'loaddemo',
		callback : function(request) {
			var obj = eval('(' + request.responseText + ')');
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
		}
	});
};

simulation.animate = function() {
	this.f = 0;
	this.refreshStrategy.activate();
};

simulation.selectNetworkArea = function() {
	this.networkLayer.removeAllFeatures();
	this.drawControl.activate();
};

simulation.createNetwork = function() {
	this.networkLayer.removeAllFeatures();
	document.getElementById("user-configuration").style.display = 'none';
	var that = this;
	OpenLayers.Request.GET({
		url : 'createnetwork',
		callback : function(request) {
			var obj = eval('(' + request.responseText + ')');
			that.network.links = obj["links"];
			that.reDrawNetwork();
		}
	});
};
