jQuery(document).ajaxStart(function() {
	simwebhelper.hideFeedback();
	simwebhelper.showIndicator();
});

jQuery(document).ajaxComplete(function() {
	simwebhelper.hideIndicator();
});

jQuery(document).ajaxError(function() {
	simwebhelper.error('Request failed!');
});

jQuery(document).ready(
		function() {

			var that = simulation;

			that.initMap();

			simwebhelper.getJson('json/network', function(data) {
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
				simwebhelper.getPanel('view/network', function() {
					simwebhelper.getJson('json/center', function(data) {
						if (data['center'] != null)
							map.setCenter(data['center'], map.numZoomLevels);
					});
				});
			});

			/* links */
			jQuery('#user-interface-links').click(function() {
				simulation.editLinks();
			});

			/* lanes */
			jQuery('#user-interface-lanes').click(function() {
				simulation.editLanes();
			});

			/* Create New Scenario */
			jQuery('#user-interface-newScenario').click(function() {
				simulation.clearLayers();
				// TODO initiate objects
				simwebhelper.getPanel('view/scenario-new');
			});

			/* json editor example */
			jQuery('#user-interface-xxx').click(function() {
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
			 * Panel, User Configuration, General
			 ******************************************************************/
			/* close panel */
			jQuery('#user-configuration').on('click',
					'#user-configuration-button-close', function() {
						simwebhelper.hidePanel();
					});
			/*******************************************************************
			 * Panel, User Configuration, Link View
			 ******************************************************************/
			/* edit link */
			jQuery('#user-configuration').on('click',
					'.user-configuration-link-edit', function() {
						var id = jQuery(this).attr('id');
						simwebhelper.getPanel('view/link-edit/' + id);
					});
			/*******************************************************************
			 * Panel, User Configuration, Link Edit
			 ******************************************************************/
			/* add lane */
			jQuery('#user-configuration').on('click',
					'.user-configuration-link-add-lane', function() {
						var id = jQuery(this).attr('id');
						simwebhelper.action('edit/addlanetolink', {
							id : id
						}, function(data) {
							// TODO upadate server json output
							that.reDrawLanes(JSON.parse(data));
						});
					});
			/* remove lane */
			jQuery('#user-configuration').on('click',
					'.user-configuration-link-remove-lane', function() {
						var ids = jQuery(this).attr('id').split('-');
						simwebhelper.action('edit/removelanefromlink', {
							laneId : ids[1],
							linkId : ids[0]
						}, function(data) {
							// TODO upadate server json output
							that.reDrawLanes(JSON.parse(data));
						});
					});
			/*******************************************************************
			 * Panel, User Configuration, New Scenario
			 ******************************************************************/
			/* select area from map */
			jQuery('#user-configuration').on('click',
					'.user-configuration-new-scenario-select', function() {
						simulation.prepareSelectArea();
						simwebhelper.hidePanel();
						simwebhelper.feedback('Please select area!');
						jQuery('#map').css('cursor', 'crosshair');
					});
			/* load from file */
			jQuery('#user-configuration').on('click',
					'.user-configuration-new-scenario-load', function() {
						// TODO work on it later
					});
			/*******************************************************************
			 * Panel, User Configuration, New Network from OSM
			 ******************************************************************/
			/* import network from osm */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-new-network-osm',
					function() {
						simwebhelper.hidePanel();
						simulation.createNetworkFromOsm(jQuery(
								'select#user-network-highway').val());
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
