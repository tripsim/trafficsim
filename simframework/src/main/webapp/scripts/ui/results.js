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
			 * Result
			 ******************************************************************/
			jQuery('#user-state-supplementary').on(
					'change',
					'#user-state-result-simulation select',
					function() {
						var simulationName = jQuery(this).val();
						var container = jQuery(this).siblings(
								'#user-state-result-selected-simulation');
						simwebhelper.replaceHtml('results/simulation/'
								+ simulationName, container);
					});
			jQuery('#user-state-supplementary').on('click',
					'#user-state-result-simulation-start-anmimation',
					function() {
						simulation.startAnimation();
					});
			jQuery('#user-state-supplementary').on('click',
					'#user-state-result-simulation-stop-anmimation',
					function() {
						simulation.stopAnimation();
					});
			/*******************************************************************
			 * Filter result by simulation
			 ******************************************************************/
			simulation.getResultParams = function() {
				var span = jQuery('#user-state-result-simulation');
				var params = {};
				params.simulationName = span.find(
						'select[name="simulationName"]').val();
				var stepSize = span.find(
						'#user-state-result-selected-simulation').attr(
						'data-step-size');
				var start = span.find('input[name="fromTime"]').val();
				var stop = span.find('input[name="toTime"]').val();
				params.offset = parseInt(start / stepSize)
				params.limit = parseInt(stop / stepSize) - params.offset;
				return params;
			}
		});