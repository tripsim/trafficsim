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
			 * Panel, User Configuration, plug ins
			 ******************************************************************/
			jQuery('#user-configuration').on('change',
					'#user-configuration-plugin-manager select', function() {
						var postData = {};
						postData.pluginName = jQuery(this).val();
						var tr = jQuery(this).closest('tr');
						postData.pluginType = tr.attr('data-plugin');
						postData.type = tr.find("td").first().text();
						simwebhelper.action('plugin/update', postData);
					});
		});