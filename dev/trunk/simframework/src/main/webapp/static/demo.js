var map;
var vehicles = {}; // key: vid  value: vehicle
var timeframes = {}; // key: fid  values: {vid: record}
var f = 0;
var vehicle = function(vid, totalFrame, type) {
	this.vid = vid;
	this.totalFrame = totalFrame;
	this.type = type;
};
var record = function(fid, x, y, vehicle) {
	this.fid = fid;
	this.x = x;
	this.y = y;
	this.vehicle = vehicle;
};

Proj4js.defs["ESRI:102643"] = "+proj=lcc +lat_1=37.06666666666667 +lat_2=38.43333333333333 +lat_0=36.5 +lon_0=-120.5 +x_0=2000000 +y_0=500000.0000000002 +ellps=GRS80 +datum=NAD83 +to_meter=0.3048006096012192 +no_defs";
Proj4js.defs["ESRI:102645"] = "+proj=lcc +lat_1=34.03333333333333 +lat_2=35.46666666666667 +lat_0=33.5 +lon_0=-118 +x_0=2000000 +y_0=500000.0000000002 +ellps=GRS80 +datum=NAD83 +to_meter=0.3048006096012192 +no_defs";
Proj4js.defs["ESRI:102243"] = "+proj=lcc +lat_1=37.06666666666667 +lat_2=38.43333333333333 +lat_0=36.5 +lon_0=-120.5 +x_0=2000000 +y_0=500000 +ellps=GRS80 +units=m +no_defs";

function init(){

	var projM = new OpenLayers.Projection("EPSG:900913");
	var projN = new OpenLayers.Projection('ESRI:102645');
	var projE = new OpenLayers.Projection('EPSG:4326');
	
	map = new OpenLayers.Map('map', {
		projection: projM,
		allOverlays: false
	});
	var osm = new OpenLayers.Layer.OSM();
	var gmap = new OpenLayers.Layer.Google('Google Streets', {
		numZoomLevels: 20
	});
	var gsat = new OpenLayers.Layer.Google(
        "Google Satellite",
        {type: google.maps.MapTypeId.SATELLITE, numZoomLevels: 22}
    );
	var ghyb = new OpenLayers.Layer.Google(
        "Google Hybrid",
        {type: google.maps.MapTypeId.HYBRID, numZoomLevels: 22, visibility: false}
    );
	map.addLayers([osm, gmap, gsat, ghyb]);
	
	map.addControl(new OpenLayers.Control.LayerSwitcher());
	var mp = new OpenLayers.Control.MousePosition({
		div: $('proj-1'),
		prefix: 'Coordinates: ',
		suffix: ' (in ' + map.getProjectionObject() + ')'
	});
	var mp1 = new OpenLayers.Control.MousePosition({
		div: $('proj-2'),
		displayProjection: projN,
		prefix: 'Coordinates: ',
		suffix: ' (in ' + projN + ')'
	});
	var mp2 = new OpenLayers.Control.MousePosition({
		div: $('proj-3'),
		displayProjection: projE,
		prefix: 'Coordinates: ',
		suffix: ' (in ' + projE + ')'
	});

	map.addControls([mp, mp1, mp2]);
	
	//var center = new OpenLayers.LonLat(6451975.037, 1872845.354).transform(projN, map.getProjectionObject()); // test.txt test2.txt
	var center = new OpenLayers.LonLat(6451136.708, 1873294.084).transform(projN, map.getProjectionObject()); // test3.txt
	map.setCenter(center, map.numZoomLevels);

	
	var request = OpenLayers.Request.GET({
		url: 'datasubset.txt',
		callback: function(request) {
			var rstrings = request.responseText.split("\n");
			for (var i in rstrings) {
				var rstring = rstrings[i].split(/\s+/);
				var v = vehicles[rstring[1]];
				if (!v) {
					v = new vehicle(rstring[1], rstring[3], rstring[11]);
					vehicles[v.vid] = v;
				}
				var f = timeframes[rstring[2]];
				if (!f) {
					timeframes[rstring[2]] = f = {} ;
				}
				f[v.vid] = new record(rstring[2], rstring[7], rstring[8], v);
			}
		}
	});


	
	var context = {
		getColor: function(feature) {
			return feature.attributes["type"] == 1 ? 'Aquamarine' :
						feature.attributes["type"] == 3 ? 'Darkorange' : 'LawnGreen';
		},
		getSize: function(feature) {
			return feature.attributes["temp"] / map.getResolution() * .703125;
		}
	};
	var template = {
		strokeOpacity: 0.8,
		strokeWidth: 0.1,
		strokeColor: 'red',
		pointRadius: "${getSize}", // using context.getSize(feature)
		fillColor: "${getColor}" // using context.getColor(feature)
	};
    var style = new OpenLayers.Style(template, {context: context});
	var polygonLayer = new OpenLayers.Layer.Vector("Polygon Layer", {
		projection: projN,
		styleMap: new OpenLayers.StyleMap(style),
        rendererOptions: {zIndexing: true},
		strategies: [new OpenLayers.Strategy.Refresh({
			interval: 100	
		})]
	});
	polygonLayer.events.register('refresh', polygonLayer, function(){
		var frame = timeframes[++f];
		var features = [];
		polygonLayer.removeAllFeatures();
		for (var i in frame) {
			var point = new OpenLayers.Geometry.Point(frame[i].x, frame[i].y);
			var feature = new OpenLayers.Feature.Vector(point.transform(polygonLayer.projection, map.getProjectionObject()), {
				temp: 4,
				type: frame[i].vehicle.type
			});
			features.push(feature);
		}
		polygonLayer.addFeatures(features);
	});
	map.addLayer(polygonLayer);
}

function start(){
	f = 0;
}
