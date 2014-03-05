jQuery(document).ready(
		function() {
			/*******************************************************************
			 * Panel, User Configuration, Simulation Result
			 ******************************************************************/
			/* start simulation */
			jQuery('#user-configuration').on('click',
					'.user-configuration-results-start-animation', function() {
						simwebhelper.hidePanel();
						simulation.startAnimation();
					});
			/* stop simulation */
			jQuery('#user-configuration').on('click',
					'.user-configuration-results-stop-animation', function() {
						simulation.stopAnimation();
					});
			/* draw trajectory */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-results-trajectory',
					function() {
						simulation.stopAnimation();
						simwebhelper.getStr('results/trajectory/'
								+ jQuery(this).closest('tr').find('select')
										.val(), function(trj) {
							simulation.drawTrajectory(trj);
						});
					});
			/* link stat plot */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-results-tsd-plot',
					function() {
						simulation.stopAnimation();
						simwebhelper.getJson('results/tsd/'
								+ jQuery(this).closest('tr').find('select')
										.val(), function(data) {
							simplot.plot(data);
						});
					});
			/* link stat close plot */
			jQuery('#user-configuration').on('click',
					'.user-configuration-results-tsd-close', function() {
						simplot.close();
					});
		});