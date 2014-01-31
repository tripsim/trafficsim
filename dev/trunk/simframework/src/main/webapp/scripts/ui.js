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

jQuery(document).ready(
		function() {

			var that = simulation;

			that.initMap();

			jQuery.getJSON('json/network', function(data) {
				if (data['links'] != null)
					that.reDrawNetwork(data['links']);
				if (data['lanes'] != null)
					that.reDrawAllLanes(data['lanes']);
				var center = new OpenLayers.LonLat(data.center[0],
						data.center[1]);
				map.setCenter(center, map.numZoomLevels);
			});

			/*******************************************************************
			 * Menu, User Interface
			 ******************************************************************/
			/* network */
			jQuery('#user-interface-network').click(function() {
				jQuery.get('view/network', function(data) {
					jQuery('#user-configuration').html(data).show();
					jQuery.getJSON('json/center', function(data) {
						if (data['center'] != null)
							map.setCenter(data['center'], map.numZoomLevels);
					});
				});
			});

			/* links */
			jQuery('#user-interface-links').click(function() {
				/*
				 * jQuery('#user-editor').show(); var container =
				 * document.getElementById("user-json-editor"); var editor = new
				 * jsoneditor.JSONEditor(container); // set json var json = {
				 * "link1" : { "StartNode" : "One", "EndNode" : "Another",
				 * "Lane" : { "width" : 3, "start" : 0, "end" : 12, "shift" : 6 } } };
				 * editor.set(json); // get json var json = editor.get();
				 */
			});

			/*******************************************************************
			 * Panel, User Configuration
			 ******************************************************************/
			/* close panel */
			jQuery('#user-configuration').on('click',
					'#user-configuration-button-close', function() {
						jQuery('#user-configuration').hide();
					});
			/* edit link */
			jQuery('#user-configuration').on('click',
					'.user-configuration-button-edit-link', function() {
						var id = jQuery(this).attr('id');
						jQuery.get('edit/link/' + id, function(data) {
							jQuery('#user-configuration').html(data).show();
						});
					});
			/* add lane */
			jQuery('#user-configuration').on('click',
					'.user-configuration-button-add-lane', function() {
						var id = jQuery(this).attr('id');
						jQuery.get('edit/addlanetolink/' + id, function(data) {
							jQuery('#user-configuration').html(data).show();
							jQuery.getJSON('json/lanes/' + id, function(data) {
								if (data != null)
									that.reDrawAllLanes(data);
							});
						});
					});
			/* remove lane */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-button-remove-lane',
					function() {
						var ids = jQuery(this).attr('id').split('-');
						jQuery.get('edit/removelane/' + ids[1] + '/fromlink/'
								+ ids[0], function(data) {
							jQuery('#user-configuration').html(data).show();
							jQuery.getJSON('json/lanes/' + ids[0], function(
									data) {
								if (data != null)
									that.reDrawAllLanes(data);
							});
						});
					});

			/*******************************************************************
			 * Test
			 ******************************************************************/
			// TEST HACK TODO
			jQuery('#test').click(
					function() {
						jQuery.get('getdemonetwork', function(data) {
							data = eval('(' + data + ')');
							if (data['links'] != null)
								that.reDrawNetwork(data['links']);
							if (data['lanes'] != null)
								that.reDrawAllLanes(data['lanes']);
							var center = new OpenLayers.LonLat(data.center[0],
									data.center[1]);
							map.setCenter(center, map.numZoomLevels);
						});
					});
			jQuery('#test-anim').click(function() {
				simulation.load();
			});
		});
