/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
jQuery(document).ready(
		function() {
			/*******************************************************************
			 * Panel, User Configuration, Simulation Settings
			 ******************************************************************/
			/* run simulation */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-run-simulation',
					function() {
						simulation.stopAnimation();
						var tbody = jQuery(this).closest('tbody');
						var postData = {
							simulationName : tbody.find(
									'input[name="simulationName"]').val(),
							duration : tbody.find('input[name="duration"]')
									.val(),
							warmup : tbody.find('input[name="warmup"]').val(),
							stepSize : tbody.find('input[name="stepSize"]')
									.val(),
							seed : tbody.find('input[name="seed"]').val()
						};
						simwebhelper.action("settings/run", postData);
						simwebhelper.hidePanel();
					});
		});