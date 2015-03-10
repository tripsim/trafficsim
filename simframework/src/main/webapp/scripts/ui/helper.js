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
var simwebhelper = {
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
	selectClass : function(className) {
		jQuery(this).siblings().removeClass(className);
		jQuery(this).addClass(className);
	},
	/***************************************************************************
	 * Request (Server Interaction)
	 **************************************************************************/
	redirect : function(url) {
		document.location = location + url;
	},
	getStr : function(url, callback) {
		jQuery.get(url, function(str) {
			if (callback)
				callback(str);
		});
	},
	getStrWithParams : function(url, params, callback) {
		jQuery.get(url, params, function(str) {
			if (callback)
				callback(str);
		});
	},
	fillHtml : function(url, container) {
		jQuery.get(url, function(str) {
			container.html(str);
		});
	},
	replaceHtml : function(url, container) {
		jQuery.get(url, function(str) {
			container.replaceWith(str);
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
		jQuery.post(url, postdata, function(data) {
			if (data.status) {
				if (data.status.successful) {
					if (callback) {
						var callbackData;
						try {
							callbackData = JSON.parse(data.data);
						} catch (e) {
							callbackData = data.data;
						}
						callback(callbackData);
					}
				}
				if (data.status.message) {
					simwebhelper.feedback(data.status.message);
				}
				if (data.status.panelUrl) {
					simwebhelper.getPanel(data.status.panelUrl);
				}
				if (data.status.redirectUrl) {
					simwebhelper.redirect(data.status.redirectUrl);
				} else {
					// simwebhelper.hidePanel();
				}
			}
		});
	}
};
