/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
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
		});