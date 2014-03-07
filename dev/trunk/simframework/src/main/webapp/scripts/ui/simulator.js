jQuery(document).ready(
		function() {
			/*******************************************************************
			 * Panel, User Configuration, Simulator
			 ******************************************************************/
			/* run simulation */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-run-simulation',
					function() {
						simulation.clearFrames();
						var tbody = jQuery(this).closest('tbody');
						var postData = {
							duration : tbody.find('input[name="duration"]')
									.val(),
							warmup : tbody.find('input[name="warmup"]').val(),
							stepSize : tbody.find('input[name="stepSize"]')
									.val(),
							seed : tbody.find('input[name="seed"]').val()
						};
						simwebhelper.action("simulator/run", postData);
						simwebhelper.hidePanel();
					});
		});