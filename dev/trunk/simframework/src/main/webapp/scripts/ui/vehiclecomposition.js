jQuery(document)
	.ready(
		function() {
		    /***********************************************************
		     * Panel, User Configuration, Vehicle Composition Edit
		     **********************************************************/
		    /**
		     * buttons for info
		     */
		    /* edit vehicle composition */
		    jQuery('#user-configuration').on(
			    'click',
			    '.user-configuration-vehicle-comp-edit',
			    function() {
				var tr = jQuery(this).closest('tr');
				simwebhelper.fillHtml(
					'vehiclecomposition/form/'
						+ tr.attr('data-name'), tr);
			    });

		    /* remove vehicle composition */
		    jQuery('#user-configuration').on(
			    'click',
			    '.user-configuration-vehicle-comp-remove',
			    function() {
				var tr = jQuery(this).closest('tr');
				simwebhelper.action(
					'vehiclecomposition/remove', {
					    name : tr.attr('data-name')
					}, function() {
					    tr.remove();
					});
			    });
		    /**
		     * buttons for form
		     */
		    /* cancel edit vehicle composition */
		    jQuery('#user-configuration')
			    .on(
				    'click',
				    '.user-configuration-vehicle-comp-cancel',
				    function() {
					var tr = jQuery(this).closest('tr');
					if (tr.attr('data-is-new') === 'true') {
					    simwebhelper
						    .action(
							    'vehiclecomposition/remove',
							    {
								name : tr
									.attr('data-name')
							    }, function() {
								tr.remove();
							    });
					} else {
					    simwebhelper
						    .fillHtml(
							    'vehiclecomposition/info/'
								    + tr
									    .attr('data-name'),
							    tr);
					}
				    });
		    /* add vehicle type in vehicle composition */
		    jQuery('#user-configuration').on(
			    'click',
			    '.user-configuration-vehicle-comp-add',
			    function() {
				jQuery(this).closest('td').before(
					jQuery(this).closest('td').prev()
						.clone());
			    });
		    /* save vehicle composition */
		    jQuery('#user-configuration').on(
			    'click',
			    '.user-configuration-vehicle-comp-save',
			    function() {
				var postData = {
				    types : [],
				    values : []
				};
				var error = '';
				var tr = jQuery(this).closest('tr');
				tr.find('td.vehiclecomposition-td').each(
					function(i, element) {
					    var type = jQuery(element).find(
						    'select').val();
					    var value = jQuery(element).find(
						    'input').val();
					    if (isNaN(value)) {
						error = value
							+ ' is not a number!';
						return false;
					    }
					    postData.types.push(type);
					    postData.values.push(value);
					});

				if (error) {
				    simwebhelper.error(error);
				}
				postData.oldName = tr.attr('data-name');
				postData.newName = tr
					.find('input[name="name"]').val();
				simwebhelper.action('vehiclecomposition/save',
					postData, function() {
					    simwebhelper.fillHtml(
						    'vehiclecomposition/info/'
							    + postData.newName,
						    tr);
					});
			    });
		    /**
		     * buttons for view
		     */
		    /* create new vehicle composition */
		    jQuery('#user-configuration')
			    .on(
				    'click',
				    '.user-configuration-vehicle-comp-new',
				    function() {
					simwebhelper
						.action(
							'vehiclecomposition/new',
							{},
							function(data) {
							    simwebhelper
								    .getStr(
									    'vehiclecomposition/form/'
										    + data
										    + ";isNew=true",
									    function(
										    html) {
										jQuery(
											'#user-configuration-vehicle-compositions')
											.append(
												html);
									    });

							});
				    });
		});