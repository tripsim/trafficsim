(function() {
	var prefix = 'resources/scripts/';
	var scripts = [ 'map.js', 'plot.js', 'ui/helper.js', 'ui/general.js',
			'ui/menu.js', 'ui/scenario-network.js', 'ui/network-link-node.js',
			'ui/lane-connection.js', 'ui/od.js', 'ui/composition.js',
			'ui/types.js', 'ui/plugins.js', 'ui/simulator.js', 'ui/results.js' ];

	var jsTags = new Array(scripts.length);
	for ( var i in scripts) {
		jsTags[i] = '<script type="text/javascript" src="' + prefix
				+ scripts[i] + '"></script>';
	}
	document.write(jsTags.join(""));

	jQuery(document).ready(function() {

		simulation.initMap();

		simwebhelper.getJson('map/network', function(data) {
			simulation.reDrawAllFeatures(data);
			simulation.editLinks();
			var center = new OpenLayers.LonLat(data.center[0], data.center[1]);
			map.setCenter(center, map.numZoomLevels);
		});
	});
}());