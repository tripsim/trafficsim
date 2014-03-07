jQuery(document).ready(
		function() {
			/*******************************************************************
			 * Panel, User Configuration, Types
			 ******************************************************************/
			/* new vehicle type */
			jQuery('#user-configuration').on('click',
					'.user-configuration-vehicle-type-new', function() {
						simwebhelper.action('types/vehicle/new', {});
					});
			/* edit vehicle type */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-vehicle-type-edit',
					function() {
						var tr = jQuery(this).closest('tr');
						simwebhelper.getPanel('types/vehicle/'
								+ tr.attr('data-name'));
					});
			/* remove vehicle type */
			jQuery('#user-configuration')
					.on(
							'click',
							'.user-configuration-vehicle-type-remove',
							function() {
								var name = jQuery(this).closest('tr').attr(
										'data-name');
								simwebhelper.action('types/vehicle/remove', {
									name : name
								});
							});
			/* save vehicle type */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-vehicle-type-save',
					function() {
						var tbody = jQuery(this).closest('tbody');
						postData = {
							name : tbody.attr('data-name'),
							newName : tbody.find('input[name="name"]').val(),
							vehicleClass : tbody.find(
									'select[name="vehicleclass"]').val(),
							width : tbody.find('input[name="width"]').val(),
							length : tbody.find('input[name="length"]').val(),
							maxAccel : tbody.find('input[name="maxaccel"]')
									.val(),
							maxDecel : tbody.find('input[name="maxdecel"]')
									.val(),
							maxSpeed : tbody.find('input[name="maxspeed"]')
									.val()
						};
						simwebhelper.action('types/vehicle/save', postData,
								function() {
									simwebhelper.getPanel('types/view');
								});
					});
			/* cancel vehicle type edit */
			jQuery('#user-configuration').on('click',
					'.user-configuration-vehicle-type-cancel', function() {
						var tbody = jQuery(this).closest('tbody');
						if (tbody.attr("data-is-new") == true)
							simwebhelper.action('types/vehicle/remove', {
								name : tbody.attr('data-name')
							});
						else
							simwebhelper.getPanel('types/view');
					});
			/* new driver type */
			jQuery('#user-configuration').on('click',
					'.user-configuration-driver-type-new', function() {
						simwebhelper.action('types/driver/new', {});
					});
			/* edit driver type */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-driver-type-edit',
					function() {
						var tr = jQuery(this).closest('tr');
						simwebhelper.getPanel('types/driver/'
								+ tr.attr('data-name'));
					});
			/* remove driver type */
			jQuery('#user-configuration')
					.on(
							'click',
							'.user-configuration-driver-type-remove',
							function() {
								var name = jQuery(this).closest('tr').attr(
										'data-name');
								simwebhelper.action('types/driver/remove/', {
									name : name
								});
							});
			/* save driver type */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-driver-type-save',
					function() {
						var tbody = jQuery(this).closest('tbody');
						postData = {
							name : tbody.attr('data-name'),
							newName : tbody.find('input[name="name"]').val(),
							perceptionTime : tbody.find(
									'input[name="perceptiontime"]').val(),
							reactionTime : tbody.find(
									'input[name="reactiontime"]').val(),
							desiredHeadway : tbody.find(
									'input[name="desiredheadway"]').val(),
							desiredSpeed : tbody.find(
									'input[name="desiredspeed"]').val()
						};
						simwebhelper.action('types/driver/save', postData,
								function() {
									simwebhelper.getPanel('types/view');
								});
					});

			/* cancel driver type edit */
			jQuery('#user-configuration').on('click',
					'.user-configuration-driver-type-cancel', function() {
						var tbody = jQuery(this).closest('tbody');
						if (tbody.attr("data-is-new") == true)
							simwebhelper.action('types/driver/remove', {
								name : tbody.attr('data-name')
							});
						else
							simwebhelper.getPanel('types/view');
					});
		});