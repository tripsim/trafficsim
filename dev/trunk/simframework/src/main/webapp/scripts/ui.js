jQuery(document).ajaxStart(function() {
	jQuery('#user-feedback').hide();
	jQuery('#ajax-indicator').css({
		width : jQuery(document).width(),
		height : jQuery(document).height()
	}).show();
});

jQuery(document).ajaxComplete(function() {
	jQuery('#ajax-indicator').hide();
});

jQuery(document).ajaxError(function() {
	jQuery('#user-feedback').html('Request failed!').show();
});

jQuery(document).ready(function() {

	jQuery.getJSON('getnetwork', function(data) {
		if (data['links'] != null)
			that.reDrawNetwork(data['links']);
		var center = new OpenLayers.LonLat(data.center[0], data.center[1]);
		map.setCenter(center, map.numZoomLevels);
	});

	jQuery('#user-interface-network').click(function() {
		jQuery.get('view/network', function(data) {
			jQuery('#user-configuration').html(data).show();
		});
	});
});
