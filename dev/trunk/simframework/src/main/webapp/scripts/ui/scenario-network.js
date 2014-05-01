jQuery(document).ready(
		function() {
			/*******************************************************************
			 * Panel, User Configuration, New Scenario
			 ******************************************************************/
			/* select area from map */
			jQuery('#user-configuration').on('click',
					'.user-configuration-scenario-select', function() {
						simulation.prepareSelectArea();
						simwebhelper.hidePanel();
						simwebhelper.feedback('Please select area!');
						jQuery('#map').css('cursor', 'crosshair');
					});
			/* import from file */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-scenario-import',
					function() {
						jQuery(this).closest('form').attr('action',
								'scenario/import').submit();
					});
			/* export to file */
			jQuery('#user-configuration').on('click',
					'.user-configuration-scenario-export', function() {
						simwebhelper.redirect('scenario/export');
					});
			/*******************************************************************
			 * Panel, User Configuration, New Network from OSM
			 ******************************************************************/
			/* import network from osm */
			jQuery('#user-configuration').on('click',
					'.user-configuration-osm-create', function() {
						simwebhelper.hidePanel();
						var url = jQuery('select#user-osm-xapi').val();
						var highway = jQuery('select#user-osm-highway').val();
						simulation.createNetworkFromOsm(url, highway);
					});
		});