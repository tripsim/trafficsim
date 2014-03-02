jQuery(document).ready(
	function() {
	    /*******************************************************************
	     * Panel, User Configuration, Link View
	     ******************************************************************/
	    /* edit link */
	    jQuery('#user-configuration').on('click',
		    '.user-configuration-link-edit', function() {
			var tbody = jQuery(this).closest('tbody');
			var id = tbody.attr('data-id');
			simwebhelper.fillHtml('link/form/' + id, tbody);
		    });
	    /* remove link */
	    jQuery('#user-configuration').on(
		    'click',
		    '.user-configuration-link-remove',
		    function() {
			var tbody = jQuery(this).closest('tbody');
			var id = tbody.attr('data-id');
			simwebhelper.action('link/remove', {
			    id : id
			}, function(data) {
			    simwebhelper.hidePanel();
			    var nodeIds = data['nodeIds'] ? data['nodeIds']
				    : [];
			    simulation.removeNetworkFeatures([ id ], nodeIds);
			    simulation.removeLaneFeatures(id);
			});
		    });
	    /* save link */
	    jQuery('#user-configuration').on(
		    'click',
		    '.user-configuration-link-save',
		    function() {
			var tbody = jQuery(this).closest('tbody');
			var id = tbody.attr('data-id');
			var postData = {
			    id : tbody.attr('data-id'),
			    name : tbody.find('input[name="name"]').val(),
			    highway : tbody.find('select[name="highway"]')
				    .val(),
			    roadName : tbody.find('input[name="roadName"]')
				    .val()
			};
			simwebhelper.action('link/save', postData, function(
				data) {
			    simwebhelper.fillHtml('link/info/' + id, tbody);
			});
		    });
	    /* cancel edit link */
	    jQuery('#user-configuration').on('click',
		    '.user-configuration-link-cancel', function() {
			var tbody = jQuery(this).closest('tbody');
			var id = tbody.attr('data-id');
			simwebhelper.fillHtml('link/info/' + id, tbody);
		    });
	});