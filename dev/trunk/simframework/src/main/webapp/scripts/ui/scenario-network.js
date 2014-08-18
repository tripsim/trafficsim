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
			 * Panel, User Configuration, New Scenario
			 ******************************************************************/
			/* select area from map */
			jQuery('#user-configuration').on('click',
					'.user-configuration-scenario-select', function() {
						simulation.prepareSelectArea();
						simwebhelper.hidePanel();
						simwebhelper.feedback('Please select area!');
						jQuery('#map').css('cursor', 'crosshair');
					});
			/* import from file */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-scenario-import',
					function() {
						jQuery(this).closest('form').attr('action',
								'scenario/import').submit();
					});
			/* export to file */
			jQuery('#user-configuration').on('click',
					'.user-configuration-scenario-export', function() {
						simwebhelper.redirect('scenario/export');
					});
			/*******************************************************************
			 * Panel, User Configuration, New Network from OSM
			 ******************************************************************/
			/* import network from osm */
			jQuery('#user-configuration').on('click',
					'.user-configuration-osm-create', function() {
						simwebhelper.hidePanel();
						var url = jQuery('select#user-osm-xapi').val();
						var highway = jQuery('select#user-osm-highway').val();
						simulation.createNetworkFromOsm(url, highway);
					});
		});