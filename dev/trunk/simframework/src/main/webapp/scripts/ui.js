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
	jQuery('#user-interface-network').click(function() {
		jQuery.get('resources/components/network.html', function(data) {
			jQuery('#user-configuration').html(data).show();
		});
	});
});
