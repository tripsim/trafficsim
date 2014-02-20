simwebhelper = {
	/***************************************************************************
	 * DOM Manipulation
	 **************************************************************************/
	panel : function(html) {
		jQuery('#user-configuration').html(html).show();
	},
	hidePanel : function() {
		jQuery('#user-configuration').hide();
	},
	feedback : function(message) {
		jQuery('#user-feedback')
				.html('<div class="info">' + message + '</div>').show();
	},
	error : function(message) {
		jQuery('#user-feedback').html('<div class="err">' + message + '</div>')
				.show();
	},
	hideFeedback : function() {
		jQuery('#user-feedback').hide();
	},
	showIndicator : function() {
		jQuery('#ajax-indicator').css({
			width : jQuery(document).width(),
			height : jQuery(document).height()
		}).show();
	},
	hideIndicator : function() {
		jQuery('#ajax-indicator').hide();
	},
	/***************************************************************************
	 * Request (Server Interaction)
	 **************************************************************************/
	getHtml : function(url, callback) {
		jQuery.get(url, function(html) {
			if (callback)
				callback(html);
		});
	},
	getPanel : function(url, callback) {
		jQuery.get(url, function(html) {
			simwebhelper.panel(html);
			if (callback)
				callback();
		});
	},
	getJson : function(url, callback) {
		jQuery.getJSON(url, function(data) {
			callback(data);
		});
	},
	action : function(url, postdata, callback) {
		jQuery
				.post(
						url,
						postdata,
						function(data) {
							if (data.status) {
								if (data.status.successful) {
								}
								if (data.status.message) {
									simwebhelper.feedback(data.status.message);
								}
								if (data.status.panelUrl) {
									simwebhelper.getPanel(data.status.panelUrl);
								} else {
									// simwebhelper.hidePanel();
								}
							}
							if (callback) {
								var callbackData = data.data ? (typeof data.data === 'object' ? data.data
										: JSON.parse(data.data))
										: {};
								callback(callbackData);
							}
						});
	}
};