jQuery(document).ready(
	function() {
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
	});