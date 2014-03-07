var simplot = {
	plot : function(data) {
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
				title : 'Time Space Diagram',
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