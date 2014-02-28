var simplot = {
	plot : function(data) {
		try {
			jQuery('#plot').empty().show();
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
		jQuery('#plot').empty().hide();
	}
};