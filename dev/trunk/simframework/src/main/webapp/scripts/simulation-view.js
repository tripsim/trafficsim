simwebhelper = {
	panel : function(html) {
		jQuery('#user-configuration').html(html).show();
	},
	hidePanel : function() {
		jQuery('#user-configuration').hide();
	},
	loadPanel : function(url, callback) {
		jQuery.get(url, function(data) {
			simwebhelper.panel(data);
			if (callback)
				callback();
		});
	},
	feedback : function(message) {
		jQuery('#user-feedback').html(message).show();
	},
	error : function(message) {
		// TODO add styles
		jQuery('#user-feedback').html(message).show();
	},
	hideFeedback : function() {
		jQuery('#user-feedback').hide();
	},
	getJson : function(url, callback) {
		jQuery.getJSON(url, function(data) {
			callback(data);
		});
	},
	post : function(url, postdata, callback) {
		jQuery.post(url, postdata, function(data) {
			callback(data);
		});
	},
	showIndicator : function() {
		jQuery('#ajax-indicator').css({
			width : jQuery(document).width(),
			height : jQuery(document).height()
		}).show();
	},
	hideIndicator : function() {
		jQuery('#ajax-indicator').hide();
	}

};