jQuery(document).ready(
	function() {

	    /*******************************************************************
	     * Ajax Global Settings
	     ******************************************************************/
	    jQuery(document).ajaxStart(function() {
		simwebhelper.hideFeedback();
		simwebhelper.showIndicator();
	    });

	    jQuery(document).ajaxComplete(function() {
		simwebhelper.hideIndicator();
	    });

	    jQuery(document).ajaxError(
		    function(event, jqxhr, settings, exception) {
			simwebhelper.error('Request failed!');
		    });

	    /*******************************************************************
	     * Panel, User Configuration, General
	     ******************************************************************/
	    /* close panel */
	    jQuery('#user-configuration').on('click',
		    '#user-configuration-button-close', function() {
			simwebhelper.hidePanel();
		    });
	});