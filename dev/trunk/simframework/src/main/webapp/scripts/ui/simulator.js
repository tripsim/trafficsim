jQuery(document).ready(
	function() {
	    /*******************************************************************
	     * Panel, User Configuration, Simulator
	     ******************************************************************/
	    /* run simulation */
	    jQuery('#user-configuration').on('click',
		    '.user-configuration-run-simulation', function() {
			simwebhelper.action("simulator/run");
			simwebhelper.hidePanel();
		    });
	});