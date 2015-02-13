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
			 * Panel, User Configuration, Simulation Settings
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
						simwebhelper.action("settings/run", postData);
						simwebhelper.hidePanel();
					});
		});