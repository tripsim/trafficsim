/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
(function() {
	var prefix = 'resources/scripts/';
	var scripts = [ 'map.js', 'plot.js', 'ui/helper.js', 'ui/general.js',
			'ui/menu.js', 'ui/project-network.js', 'ui/network-link-node.js',
			'ui/lane-connection.js', 'ui/od.js', 'ui/composition.js',
			'ui/types.js', 'ui/plugins.js', 'ui/settings.js', 'ui/results.js',
			'ui/json-editor.js' ];

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