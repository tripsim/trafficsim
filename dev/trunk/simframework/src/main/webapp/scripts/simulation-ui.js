jQuery(document).ajaxStart(function() {
	simwebhelper.hideFeedback();
	simwebhelper.showIndicator();
});

jQuery(document).ajaxComplete(function() {
	simwebhelper.hideIndicator();
});

jQuery(document).ajaxError(function() {
	simwebhelper.error('Request failed!');
});

jQuery(document)
		.ready(
				function() {

					var that = simulation;

					that.initMap();

					simwebhelper.getJson('json/network',
							function(data) {
								if (data['links'] != null)
									that.reDrawNetwork(data['links']);
								if (data['lanes'] != null
										&& data['connectors'] != null)
									that.reDrawAllLanes(data['lanes'],
											data['connectors']);
								var center = new OpenLayers.LonLat(
										data.center[0], data.center[1]);
								map.setCenter(center, map.numZoomLevels);
							});

					/***********************************************************
					 * Menu, User Interface
					 **********************************************************/
					/* network */
					jQuery('#user-interface-network')
							.click(
									function() {
										simwebhelper
												.getPanel(
														'view/network',
														function() {
															simwebhelper
																	.getJson(
																			'json/center',
																			function(
																					data) {
																				if (data['center'] != null)
																					map
																							.setCenter(
																									data['center'],
																									map.numZoomLevels);
																			});
														});
									});

					/* links */
					jQuery('#user-interface-links').click(function() {
						simwebhelper.hidePanel();
						simulation.editLinks();
					});

					/* lanes */
					jQuery('#user-interface-lanes').click(function() {
						simwebhelper.hidePanel();
						simulation.editLanes();
					});

					/* nodes */
					jQuery('#user-interface-nodes').click(function() {
						simwebhelper.hidePanel();
						simulation.editNodes();
					});

					/* vehicle composition */
					jQuery('#user-interface-vehicle-composition').click(
							function() {
								simwebhelper
										.getPanel('view/vehiclecomposition');
							});

					/* Create New Scenario */
					jQuery('#user-interface-newScenario').click(function() {
						simulation.clearLayers();
						// TODO initiate objects
						simwebhelper.getPanel('view/scenario-new');
					});

					/* json editor example */
					jQuery('#user-interface-xxx').click(function() {
						/*
						 * jQuery('#user-editor').show(); var container =
						 * document.getElementById("user-json-editor"); var
						 * editor = new jsoneditor.JSONEditor(container); // set
						 * json var json = { "link1" : { "StartNode" : "One",
						 * "EndNode" : "Another", "Lane" : { "width" : 3,
						 * "start" : 0, "end" : 12, "shift" : 6 } } };
						 * editor.set(json); // get json var json =
						 * editor.get();
						 */
					});

					/***********************************************************
					 * Panel, User Configuration, General
					 **********************************************************/
					/* close panel */
					jQuery('#user-configuration').on('click',
							'#user-configuration-button-close', function() {
								simwebhelper.hidePanel();
							});
					/***********************************************************
					 * Panel, User Configuration, Link View
					 **********************************************************/
					/* edit link */
					jQuery('#user-configuration').on('click',
							'.user-configuration-link-edit', function() {
								var id = jQuery(this).attr('data-id');
								simwebhelper.getPanel('edit/link/' + id);
							});
					/***********************************************************
					 * Panel, User Configuration, Link Edit
					 **********************************************************/
					/* add lane */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-link-add-lane',
							function() {
								var id = jQuery(this).attr('data-id');
								simwebhelper.action('action/addlanetolink', {
									id : id
								}, function(data) {
									that.reDrawLanes(data.lanes,
											data.connectors);
								});
							});
					/* remove lane */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-link-remove-lane',
							function() {
								var ids = jQuery(this).attr('data-id').split(
										'-');
								simwebhelper.action(
										'action/removelanefromlink', {
											laneId : ids[1],
											linkId : ids[0]
										}, function(data) {
											that.reDrawLanes(data.lanes,
													data.connectors);
										});
							});
					/***********************************************************
					 * Panel, User Configuration, Connector Edit
					 **********************************************************/
					/* remove connector */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-connector-remove',
							function() {
								var ids = jQuery(this).attr('data-id').split(
										'-');
								simwebhelper.action('action/removeconnector', {
									fromLink : ids[0],
									fromLane : ids[1],
									toLink : ids[2],
									toLane : ids[3]
								}, function(data) {
									that.removeConnector(data);
									simwebhelper.hidePanel();
								});
							});
					/***********************************************************
					 * Panel, User Configuration, Node Ods
					 **********************************************************/
					/* edit od */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-od-edit',
							function() {
								var tr = jQuery(this).parent().parent();
								simwebhelper.getHtml('edit/od-form/'
										+ jQuery(this).attr('data-id'),
										function(html) {
											tr.replaceWith(html);
										});
							});
					/* cancel edit od */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-od-cancel',
							function() {
								var tr = jQuery(this).parent().parent();
								simwebhelper.getHtml('edit/od-view/'
										+ jQuery(this).attr('data-id'),
										function(html) {
											tr.replaceWith(html);
										});
							});
					/* add interval in od */
					jQuery('#user-configuration').on('click',
							'.user-configuration-od-add-int', function() {
								// jQuery(this).parent().before(
								// jQuery(this).parent().prev().clone());
							});
					/* remove interval in od */
					jQuery('#user-configuration').on('click',
							'.user-configuration-vehicle-od-remove-int',
							function() {
								// jQuery(this).parent().before(
								// jQuery(this).parent().prev().clone());
							});
					/* save od */
					jQuery('#user-configuration').on('click',
							'.user-configuration-od-save', function() {
								// TODO
							});
					/* create new od */
					jQuery('#user-configuration')
							.on(
									'click',
									'.user-configuration-od-new',
									function() {
										simwebhelper
												.action(
														'action/newod',
														{
															originId : jQuery(
																	this).attr(
																	'data-id'),
															destinationId : 0
														},
														function(data) {
															simwebhelper
																	.getHtml(
																			'edit/od-form/'
																					+ data,
																			function(
																					html) {
																				jQuery(
																						'#user-configuration-ods-tbody')
																						.append(
																								html);
																			});

														});
									});
					/* remove od */
					jQuery('#user-configuration').on('click',
							'.user-configuration-od-remove', function() {
								var tr = jQuery(this).parent().parent();
								simwebhelper.action('action/removeod', {
									id : jQuery(this).attr('data-id')
								}, function() {
									tr.remove();
								});
							});
					/***********************************************************
					 * Panel, User Configuration, Vehicle Composition Edit
					 **********************************************************/
					/* edit vehicle composition */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-vehicle-comp-edit',
							function() {
								var tr = jQuery(this).parent().parent();
								simwebhelper.getHtml(
										'edit/vehiclecomposition-form/'
												+ jQuery(this).attr('data-id'),
										function(html) {
											tr.replaceWith(html);
										});
							});
					/* cancel edit vehicle composition */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-vehicle-comp-cancel',
							function() {
								var tr = jQuery(this).parent().parent();
								simwebhelper.getHtml(
										'edit/vehiclecomposition-view/'
												+ jQuery(this).attr('data-id'),
										function(html) {
											tr.replaceWith(html);
										});
							});
					/* add vehicle type in vehicle composition */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-vehicle-comp-add',
							function() {
								jQuery(this).parent().before(
										jQuery(this).parent().prev().clone());
							});
					/* save vehicle composition */
					jQuery('#user-configuration')
							.on(
									'click',
									'.user-configuration-vehicle-comp-save',
									function() {
										var postData = {
											types : [],
											values : []
										};
										var error = '';
										jQuery(this)
												.parent()
												.parent()
												.find(
														'td.vehiclecomposition-td')
												.each(
														function(i, element) {
															var type = jQuery(
																	element)
																	.find(
																			'select')
																	.val();
															var value = jQuery(
																	element)
																	.find(
																			'input:text')
																	.val();
															if (isNaN(value)) {
																error = value
																		+ ' is not a number!';
																return false;
															}
															postData.types
																	.push(type);
															postData.values
																	.push(value);
														});

										if (error) {
											simwebhelper.error(error);
										}
										postData.id = jQuery(this).attr(
												'data-id');
										postData.name = jQuery(this)
												.parent()
												.parent()
												.find(
														'td.vehiclecomposition-td-name input:text')
												.val();
										var tr = jQuery(this).parent().parent();
										simwebhelper
												.action(
														'action/savevehiclecomposition',
														postData,
														function() {
															simwebhelper
																	.getHtml(
																			'edit/vehiclecomposition-view/'
																					+ postData.id,
																			function(
																					html) {
																				tr
																						.replaceWith(html);
																			});
														});
									});
					/* create new vehicle composition */
					jQuery('#user-configuration')
							.on(
									'click',
									'.user-configuration-vehicle-comp-new',
									function() {
										simwebhelper
												.action(
														'action/newvehiclecomposition',
														{},
														function(data) {
															simwebhelper
																	.getHtml(
																			'edit/vehiclecomposition-form/'
																					+ data,
																			function(
																					html) {
																				jQuery(
																						'#user-configuration-vehicle-compositions-tbody')
																						.append(
																								html);
																			});

														});
									});
					/* remove vehicle composition */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-vehicle-comp-remove',
							function() {
								var tr = jQuery(this).parent().parent();
								simwebhelper.action(
										'action/removevehiclecomposition', {
											id : jQuery(this).attr('data-id')
										}, function() {
											tr.remove();
										});
							});
					/***********************************************************
					 * Panel, User Configuration, New Scenario
					 **********************************************************/
					/* select area from map */
					jQuery('#user-configuration').on('click',
							'.user-configuration-new-scenario-select',
							function() {
								simulation.prepareSelectArea();
								simwebhelper.hidePanel();
								simwebhelper.feedback('Please select area!');
								jQuery('#map').css('cursor', 'crosshair');
							});
					/* load from file */
					jQuery('#user-configuration').on('click',
							'.user-configuration-new-scenario-load',
							function() {
								// TODO work on it later
							});
					/***********************************************************
					 * Panel, User Configuration, New Network from OSM
					 **********************************************************/
					/* import network from osm */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-new-network-osm',
							function() {
								simwebhelper.hidePanel();
								simulation.createNetworkFromOsm(jQuery(
										'select#user-network-highway').val());
							});

					/***********************************************************
					 * Test
					 **********************************************************/
					// TEST HACK TODO
					jQuery('#test').click(
							function() {
								jQuery.get('getdemonetwork', function(data) {
									data = eval('(' + data + ')');
									if (data['links'] != null)
										that.reDrawNetwork(data['links'],
												data['nodes']);
									if (data['lanes'] != null
											|| data['connectors'] != null)
										that.reDrawAllLanes(data['lanes'],
												data['connectors']);
									var center = new OpenLayers.LonLat(
											data.center[0], data.center[1]);
									map.setCenter(center, map.numZoomLevels);
								});
							});
					jQuery('#test-anim').click(function() {
						simulation.load();
					});
				});
