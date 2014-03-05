jQuery(document).ready(
		function() {
			/*******************************************************************
			 * Panel, User Configuration, Node Ods
			 ******************************************************************/
			/**
			 * buttons for info
			 */
			/* edit od */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-od-edit',
					function() {
						var tr = jQuery(this).closest('tr');
						simwebhelper.fillHtml('od/form/' + tr.attr('data-id'),
								tr);
					});
			/* remove od */
			jQuery('#user-configuration').on('click',
					'.user-configuration-od-remove', function() {
						var tr = jQuery(this).closest('tr');
						simwebhelper.action('od/remove', {
							id : tr.attr('data-id')
						}, function() {
							tr.remove();
						});
					});
			/**
			 * buttons for form
			 */
			/* cancel edit od */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-od-cancel',
					function() {
						var tr = jQuery(this).closest('tr');
						if (tr.attr('data-is-new') === 'true') {
							simwebhelper.action('od/remove', {
								id : tr.attr('data-id')
							}, function() {
								tr.remove();
							});
						} else {
							simwebhelper.fillHtml('od/info/'
									+ tr.attr('data-id'), tr);
						}
					});
			/* add interval in od */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-od-add-int',
					function() {
						jQuery(this).closest('td').before(
								jQuery(this).closest('td').prev().clone());
					});
			/* remove interval in od */
			jQuery('#user-configuration').on('click',
					'.user-configuration-od-remove-int', function() {
						if (jQuery(this).closest('td').index() > 4)
							jQuery(this).closest('td').prev().remove();
					});
			/* save od */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-od-save',
					function() {
						var postData = {
							times : [],
							vphs : []
						};
						var error = '';
						var tr = jQuery(this).closest('tr');
						tr.find('td.od-td').each(
								function(i, element) {
									var time = jQuery(element).find(
											'input[name="time"]').val();
									var vph = jQuery(element).find(
											'input[name="vph"]').val();
									if (isNaN(time)) {
										error = time + ' is not a number!';
										return false;
									}
									if (isNaN(vph)) {
										error = vph + ' is not a number!';
										return false;
									}
									postData.times.push(time);
									postData.vphs.push(vph);
								});

						if (error) {
							simwebhelper.error(error);
							return;
						}
						postData.id = tr.attr('data-id');
						postData.destinatioId = tr.find(
								'select[name="destination"]').val();
						postData.vehicleCompositionName = tr.find(
								'select[name="composition"]').val();
						simwebhelper.action('od/save', postData,
								function() {
									simwebhelper.fillHtml('od/info/'
											+ postData.id, tr);
								});

					});
			/**
			 * buttons for view
			 */
			/* create new od */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-od-new',
					function() {
						var tr = jQuery(this).closest('tr');
						simwebhelper.action('od/new', {
							originId : tr.attr('data-id'),
							destinationId : 0
						}, function(data) {
							simwebhelper.getStr('od/form/' + data
									+ ";isNew=true", function(html) {
								jQuery('#user-configuration-ods-tbody').append(
										html);
							});

						});
					});

		});