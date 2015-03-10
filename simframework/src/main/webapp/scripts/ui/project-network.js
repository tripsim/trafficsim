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
			 * Panel, User Configuration, New Project
			 ******************************************************************/
			/* start new project */
			jQuery('#user-configuration').on('click',
					'.user-configuration-project-new', function() {
						simwebhelper.redirect('new');
					});
			/* select area from map */
			jQuery('#user-configuration').on('click',
					'.user-configuration-project-select', function() {
						simulation.prepareSelectArea();
						simwebhelper.hidePanel();
						simwebhelper.feedback('Please select area!');
						jQuery('#map').css('cursor', 'crosshair');
					});
			/* import from file */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-project-import',
					function() {
						jQuery(this).closest('form').attr('action',
								'project/import').submit();
					});
			/* export to file */
			jQuery('#user-configuration').on('click',
					'.user-configuration-project-export', function() {
						simwebhelper.redirect('project/export');
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
			/*******************************************************************
			 * Panel, User Configuration, Load Network, OdMatrix
			 ******************************************************************/
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-project-load',
					function() {
						var select = jQuery(this).closest("tr").find('select')
								.first();
						var postData = {};
						postData.name = select.val();
						postData.element = select.attr("name");
						simwebhelper.action('project/load', postData);
					});
			/*******************************************************************
			 * Panel, User Configuration, Save Network, OdMatrix
			 ******************************************************************/
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-project-save',
					function() {
						var postData = {};
						postData.name = prompt("Please enter a name: ");
						postData.element = jQuery(this).closest('tr').attr(
								'data-element');
						simwebhelper.action('project/save', postData);
					});
		});