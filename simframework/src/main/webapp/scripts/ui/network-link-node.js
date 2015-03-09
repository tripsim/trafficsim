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
			 * Panel, User Configuration, Link View
			 ******************************************************************/
			/* edit link */
			jQuery('#user-configuration').on('click',
					'.user-configuration-link-edit', function() {
						var tbody = jQuery(this).closest('tbody');
						var id = tbody.attr('data-id');
						simwebhelper.replaceHtml('link/form/' + id, tbody);
					});
			/* remove link */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-link-remove',
					function() {
						var tbody = jQuery(this).closest('tbody');
						var id = tbody.attr('data-id');
						simwebhelper.action('link/remove', {
							id : id
						}, function(data) {
							simwebhelper.hidePanel();
							var nodeIds = data['nodeIds'] ? data['nodeIds']
									: [];
							simulation.removeNetworkFeatures([ id ], nodeIds);
							simulation.removeLaneFeatures(id);
							var lanesconnectors = JSON
									.parse(data['lanesconnectors']);
							simulation.reDrawLanes(lanesconnectors.lanes,
									lanesconnectors.connectors);
						});
					});
			/* save link */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-link-save',
					function() {
						var tbody = jQuery(this).closest('tbody');
						var id = tbody.attr('data-id');
						var postData = {
							id : tbody.attr('data-id'),
							desc : tbody.find('input[name="desc"]').val(),
							highway : tbody.find('select[name="highway"]')
									.val(),
							roadName : tbody.find('input[name="roadName"]')
									.val()
						};
						simwebhelper.action('link/save', postData, function(
								data) {
							simwebhelper.replaceHtml('link/info/' + id, tbody);
						});
					});
			/* cancel edit link */
			jQuery('#user-configuration').on('click',
					'.user-configuration-link-cancel', function() {
						var tbody = jQuery(this).closest('tbody');
						var id = tbody.attr('data-id');
						simwebhelper.replaceHtml('link/info/' + id, tbody);
					});
			/* create reverse link */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-link-create-reverse',
					function() {
						var tbody = jQuery(this).closest('tbody');
						var id = tbody.attr('data-id');
						simwebhelper.action('link/createreverse', {
							id : id
						}, function(data) {
							var links = JSON.parse(data['link']);
							var lanesconnectors = JSON
									.parse(data['lanesconnectors']);
							simulation.reDrawNetworkFeatures(links, []);
							simulation.reDrawLanes(lanesconnectors.lanes,
									lanesconnectors.connectors);
						});
					});
			/*******************************************************************
			 * Panel, User Configuration, Node View
			 ******************************************************************/
			/* edit node */
			jQuery('#user-configuration').on('click',
					'.user-configuration-node-edit', function() {
						var tbody = jQuery(this).closest('tbody');
						var id = tbody.attr('data-id');
						simwebhelper.replaceHtml('node/form/' + id, tbody);
					});
			/* save node */
			jQuery('#user-configuration').on(
					'click',
					'.user-configuration-node-save',
					function() {
						var tbody = jQuery(this).closest('tbody');
						var id = tbody.attr('data-id');
						var postData = {
							id : tbody.attr('data-id'),
							desc : tbody.find('input[name="desc"]').val(),
						};
						simwebhelper.action('node/save', postData, function(
								data) {
							simwebhelper.replaceHtml('node/info/' + id, tbody);
						});
					});
			/* cancel edit node */
			jQuery('#user-configuration').on('click',
					'.user-configuration-node-cancel', function() {
						var tbody = jQuery(this).closest('tbody');
						var id = tbody.attr('data-id');
						simwebhelper.replaceHtml('node/info/' + id, tbody);
					});
		});