jQuery(document).ready(function() {

    /***************************************************************************
     * Menu, User Interface
     **************************************************************************/
    /* network */
    jQuery('#user-interface-network').click(function() {
	simwebhelper.getPanel('network/view', function() {
	    simwebhelper.getJson('map/center', function(data) {
		if (data['center'] != null)
		    map.setCenter(data['center'], map.numZoomLevels);
	    });
	});
    });

    /* links */
    jQuery('#user-interface-links').click(function() {
	simwebhelper.hidePanel();
	simulation.editLinks();
    });

    /* lanes */
    jQuery('#user-interface-lanes').click(function() {
	simwebhelper.hidePanel();
	simulation.editLanes();
    });

    /* list */
    jQuery('#user-interface-list').click(function() {
	// TODO show list of nodes, links, ods
    });

    /* vehicle composition */
    jQuery('#user-interface-vehicle-composition').click(function() {
	simwebhelper.getPanel('vehiclecomposition/view');
    });

    /* simulator */
    jQuery('#user-interface-simulator').click(function() {
	simwebhelper.getPanel('simulator/view');
    });

    /* router */
    jQuery('#user-interface-router').click(function() {
	simwebhelper.getPanel('router/view');
    });

    /* simulation result */
    jQuery('#user-interface-results').click(function() {
	simwebhelper.getPanel('results/view');
    });

    /* Create New Scenario */
    jQuery('#user-interface-newScenario').click(function() {
	simulation.clearLayers();
	// TODO initiate objects
	simwebhelper.getPanel('scenario/new');
    });

    /* json editor example */
    jQuery('#user-interface-xxx').click(function() {
	/*
	 * jQuery('#user-editor').show(); var container =
	 * document.getElementById("user-json-editor"); var editor = new
	 * jsoneditor.JSONEditor(container); // set json var json = { "link1" : {
	 * "StartNode" : "One", "EndNode" : "Another", "Lane" : { "width" : 3,
	 * "start" : 0, "end" : 12, "shift" : 6 } } }; editor.set(json); // get
	 * json var json = editor.get();
	 */
    });

    /***************************************************************************
     * Test
     **************************************************************************/
    // TEST HACK TODO
    jQuery('#test').click(function() {
	jQuery.get('getdemonetwork', function(data) {
	    data = eval('(' + data + ')');
	    if (data['links'] != null || data['nodes'] != null)
		simulation.reDrawNetwork(data['links'], data['nodes']);
	    if (data['lanes'] != null || data['connectors'] != null)
		simulation.reDrawAllLanes(data['lanes'], data['connectors']);
	    var center = new OpenLayers.LonLat(data.center[0], data.center[1]);
	    map.setCenter(center, map.numZoomLevels);
	});
    });
    jQuery('#test-anim').click(function() {
	simulation.load();
    });
});
