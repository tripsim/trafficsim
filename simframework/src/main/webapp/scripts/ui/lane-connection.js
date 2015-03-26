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
	     * Panel, User Configuration, Link Edit
	     ******************************************************************/
	    /* add lane */
	    jQuery('#user-configuration').on(
		    'click',
		    '.user-configuration-link-add-lane',
		    function() {
			var table = jQuery(this).closest('table');
			var id = table.attr('data-id');
			simwebhelper.action('lane/addtolink', {
			    id : id
			},
				function(data) {
				    simulation.reDrawLanes(data.lanes,
					    data.connectors);
				    simwebhelper.replaceHtml('lane/' + id, table);
				});
		    });
	    /* remove lane */
	    jQuery('#user-configuration').on(
		    'click',
		    '.user-configuration-link-remove-lane',
		    function() {
			var tr = jQuery(this).closest('tr');
			var table = tr.closest('table');
			var ids = tr.attr('data-id').split('-');
			simwebhelper.action('lane/removefromlink', {
				lanePosition : ids[1],
			    linkId : ids[0]
			},
				function(data) {
				    simulation.reDrawLanes(data.lanes,
					    data.connectors);
				    simwebhelper.replaceHtml('lane/' + ids[0],
					    table);
				});
		    });
	    /* edit lane */
	    jQuery('#user-configuration')
		    .on(
			    'click',
			    '.user-configuration-link-edit-lane',
			    function() {
				jQuery(this).closest('table').find(
					'.user-configuration-link-cancel-lane')
					.click();
				var tr = jQuery(this).closest('tr');
				var ids = tr.attr('data-id').split('-');
				simwebhelper.replaceHtml('lane/form/' + ids[0]
					+ ";lanePosition=" + ids[1], tr);
			    });
	    /* cancel edit lane */
	    jQuery('#user-configuration').on(
		    'click',
		    '.user-configuration-link-cancel-lane',
		    function() {
			var tr = jQuery(this).closest('tr');
			var ids = tr.attr('data-id').split('-');
			simwebhelper.replaceHtml('lane/info/' + ids[0]
				+ ";lanePosition=" + ids[1], tr);
		    });
	    /* save edit lane */
	    jQuery('#user-configuration').on(
		    'click',
		    '.user-configuration-link-save-lane',
		    function() {
			var tr = jQuery(this).closest('tr');
			var ids = tr.attr('data-id').split('-');
			postData = {
			    linkId : ids[0],
			    lanePosition : ids[1],
			    start : tr.find('input[name="start"]').val(),
			    end : tr.find('input[name="end"]').val(),
			    width : tr.find('input[name="width"]').val()
			};
			simwebhelper.action('lane/save', postData,
				function(data) {
				    simwebhelper.replaceHtml('lane/info/' + ids[0]
					    + ";lanePosition=" + ids[1], tr);
				    simulation.reDrawLanes(data.lanes,
					    data.connectors);
				});
		    });
	    /*******************************************************************
	     * Panel, User Configuration, Connector Edit
	     ******************************************************************/
	    /* remove connector */
	    jQuery('#user-configuration').on('click',
		    '.user-configuration-connector-remove', function() {
			var ids = jQuery(this).attr('data-id').split('-');
			simwebhelper.action('connector/remove', {
			    fromLink : ids[0],
			    fromLane : ids[1],
			    toLink : ids[2],
			    toLane : ids[3]
			}, function(data) {
			    simulation.removeConnector(data);
			    simwebhelper.hidePanel();
			});
		    });
	});