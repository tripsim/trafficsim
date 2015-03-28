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
var simplot = {
	plot : function(data, title) {
		try {
			var width = jQuery(document).width();
			var height = jQuery(document).height();
			jQuery('#popup').empty().css({
				width : width,
				height : height
			}).show();
			jQuery('<div id="plot"></plot>').appendTo('#popup').css({
				top : height * 0.25,
				left : width * 0.25,
				width : width * 0.50,
				height : height * 0.50
			});
			jQuery(document).keyup(simplot.close);
			jQuery.jqplot('plot', data, {
				title : title,
				seriesDefaults : {
					showMarker : false
				},
				axesDefaults : {
					labelRenderer : jQuery.jqplot.CanvasAxisLabelRenderer
				},
				axes : {
					xaxis : {
						label : 'Time',
						pad : 0
					},
					yaxis : {
						label : 'Space',
						pad : 0
					}
				}
			});
		} catch (e) {
			jQuery('#plot').html('<p>data not available</p>').show();
		}
	},
	close : function() {
		jQuery('#popup').empty().hide();
		jQuery(document).unbind('keyup', simplot.close);
	}
};