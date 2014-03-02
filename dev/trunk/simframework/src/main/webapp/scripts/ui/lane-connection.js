jQuery(document).ready(
	function() {
	    /*******************************************************************
	     * Panel, User Configuration, Link Edit
	     ******************************************************************/
	    /* add lane */
	    jQuery('#user-configuration').on(
		    'click',
		    '.user-configuration-link-add-lane',
		    function() {
			var table = jQuery(this).closest('table');
			var id = table.attr('data-id');
			simwebhelper.action('lane/addtolink', {
			    id : id
			},
				function(data) {
				    simulation.reDrawLanes(data.lanes,
					    data.connectors);
				    simwebhelper.getStr('lane/' + id, function(
					    data) {
					table.replaceWith(data);
				    });
				});
		    });
	    /* remove lane */
	    jQuery('#user-configuration').on(
		    'click',
		    '.user-configuration-link-remove-lane',
		    function() {
			var tr = jQuery(this).closest('tr');
			var table = tr.closest('table');
			var ids = tr.attr('data-id').split('-');
			simwebhelper.action('lane/removefromlink', {
			    laneId : ids[1],
			    linkId : ids[0]
			},
				function(data) {
				    simulation.reDrawLanes(data.lanes,
					    data.connectors);
				    simwebhelper.getStr('lane/' + ids[0],
					    function(data) {
						table.replaceWith(data);
					    });
				});
		    });
	    /* edit lane */
	    jQuery('#user-configuration')
		    .on(
			    'click',
			    '.user-configuration-link-edit-lane',
			    function() {
				jQuery(this).closest('table').find(
					'.user-configuration-link-cancel-lane')
					.click();
				var tr = jQuery(this).closest('tr');
				var ids = tr.attr('data-id').split('-');
				simwebhelper.getStr('lane/form/' + ids[0]
					+ ";laneId=" + ids[1], function(data) {
				    tr.replaceWith(data);
				});
			    });
	    /* cancel edit lane */
	    jQuery('#user-configuration').on(
		    'click',
		    '.user-configuration-link-cancel-lane',
		    function() {
			var tr = jQuery(this).closest('tr');
			var ids = tr.attr('data-id').split('-');
			simwebhelper.getStr('lane/info/' + ids[0] + ";laneId="
				+ ids[1], function(data) {
			    tr.replaceWith(data);
			});
		    });
	    /* save edit lane */
	    jQuery('#user-configuration').on(
		    'click',
		    '.user-configuration-link-save-lane',
		    function() {
			var tr = jQuery(this).closest('tr');
			var ids = tr.attr('data-id').split('-');
			postData = {
			    linkId : ids[0],
			    laneId : ids[1],
			    start : tr.find('input[name="start"]').val(),
			    end : tr.find('input[name="end"]').val(),
			    width : tr.find('input[name="width"]').val()
			};
			simwebhelper.action('lane/save', postData,
				function(data) {
				    simwebhelper.getStr('lane/info/' + ids[0]
					    + ";laneId=" + ids[1], function(
					    data) {
					tr.replaceWith(data);
				    });
				    simulation.reDrawLanes(data.lanes,
					    data.connectors);
				});
		    });
	    /*******************************************************************
	     * Panel, User Configuration, Connector Edit
	     ******************************************************************/
	    /* remove connector */
	    jQuery('#user-configuration').on('click',
		    '.user-configuration-connector-remove', function() {
			var ids = jQuery(this).attr('data-id').split('-');
			simwebhelper.action('connector/remove', {
			    fromLink : ids[0],
			    fromLane : ids[1],
			    toLink : ids[2],
			    toLane : ids[3]
			}, function(data) {
			    simulation.removeConnector(data);
			    simwebhelper.hidePanel();
			});
		    });
	});