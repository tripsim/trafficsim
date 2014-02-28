jQuery(document).ajaxStart(function() {
	simwebhelper.hideFeedback();
	simwebhelper.showIndicator();
});

jQuery(document).ajaxComplete(function() {
	simwebhelper.hideIndicator();
});

jQuery(document).ajaxError(function(event, jqxhr, settings, exception) {
	simwebhelper.error('Request failed!');
});

jQuery(document)
		.ready(
				function() {

					var that = simulation;

					that.initMap();

					simwebhelper.getJson('map/network',
							function(data) {
								if (data['links'] != null
										|| data['nodes'] != null)
									that.reDrawNetwork(data['links'],
											data['nodes']);
								if (data['lanes'] != null
										&& data['connectors'] != null)
									that.reDrawAllLanes(data['lanes'],
											data['connectors']);
								that.editLinks();
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
														'network/view',
														function() {
															simwebhelper
																	.getJson(
																			'map/center',
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

					/* list */
					jQuery('#user-interface-list').click(function() {
						// TODO show list of nodes, links, ods
					});

					/* vehicle composition */
					jQuery('#user-interface-vehicle-composition').click(
							function() {
								simwebhelper
										.getPanel('vehiclecomposition/view');
							});

					/* simulator */
					jQuery('#user-interface-simulator').click(function() {
						simwebhelper.getPanel('simulator/view');
					});

					/* router */
					jQuery('#user-interface-router').click(function() {
						simwebhelper.getPanel('router/view');
					});

					/* simulation result */
					jQuery('#user-interface-results').click(function() {
						simwebhelper.getPanel('results/view');
					});

					/* Create New Scenario */
					jQuery('#user-interface-newScenario').click(function() {
						simulation.clearLayers();
						// TODO initiate objects
						simwebhelper.getPanel('scenario/new');
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
								// TODO work on link edit
							});
					/***********************************************************
					 * Panel, User Configuration, Link Edit
					 **********************************************************/
					/* add lane */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-link-add-lane',
							function() {
								var id = jQuery(this).closest('td').attr(
										'data-id');
								simwebhelper.action('lane/addtolink', {
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
								var ids = jQuery(this).closest('td').attr(
										'data-id').split('-');
								simwebhelper.action('lane/removefromlink', {
									laneId : ids[1],
									linkId : ids[0]
								}, function(data) {
									that.reDrawLanes(data.lanes,
											data.connectors);
								});
							});
					/* edit lane */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-link-edit-lane',
							function() {
								jQuery(this).closest('table').find(
										'.user-configuration-link-cancel-lane')
										.click();
								var ids = jQuery(this).closest('td').attr(
										'data-id').split('-');
								var tr = jQuery(this).closest('tr');
								simwebhelper.getStr('lane/form/' + ids[0]
										+ ";laneId=" + ids[1], function(data) {
									tr.replaceWith(data);
								});
							});
					/* cancel edit lane */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-link-cancel-lane',
							function() {
								var ids = jQuery(this).closest('td').attr(
										'data-id').split('-');
								var tr = jQuery(this).closest('tr');
								simwebhelper.getStr('lane/info/' + ids[0]
										+ ";laneId=" + ids[1], function(data) {
									tr.replaceWith(data);
								});
							});
					/* save edit lane */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-link-save-lane',
							function() {
								var ids = jQuery(this).closest('td').attr(
										'data-id').split('-');
								var tr = jQuery(this).closest('tr');
								postData = {
									linkId : ids[0],
									laneId : ids[1],
									start : tr.find('input[name="start"]')
											.val(),
									end : tr.find('input[name="end"]').val(),
									width : tr.find('input[name="width"]')
											.val()
								};
								simwebhelper.action('lane/save', postData,
										function(data) {
											simwebhelper.getStr('lane/info/'
													+ ids[0] + ";laneId="
													+ ids[1], function(data) {
												tr.replaceWith(data);
											});
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
								simwebhelper.action('connector/remove', {
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
								var tr = jQuery(this).closest('tr');
								simwebhelper.getStr('od/form/'
										+ jQuery(this).closest('td').attr(
												'data-id'), function(html) {
									tr.replaceWith(html);
								});
							});
					/* cancel edit od */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-od-cancel',
							function() {
								var tr = jQuery(this).closest('tr');
								if (jQuery(this).closest('td').attr(
										'data-is-new') === 'true') {
									simwebhelper.action('od/remove', {
										id : jQuery(this).closest('td').attr(
												'data-id')
									}, function() {
										tr.remove();
									});
								} else {
									simwebhelper.getStr('od/info/'
											+ jQuery(this).closest('td').attr(
													'data-id'), function(html) {
										tr.replaceWith(html);
									});
								}
							});
					/* remove od */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-od-remove',
							function() {
								var tr = jQuery(this).closest('tr');
								simwebhelper.action('od/remove', {
									id : jQuery(this).closest('td').attr(
											'data-id')
								}, function() {
									tr.remove();
								});
							});
					/* add interval in od */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-od-add-int',
							function() {
								jQuery(this).closest('td').before(
										jQuery(this).closest('td').prev()
												.clone());
							});
					/* remove interval in od */
					jQuery('#user-configuration').on('click',
							'.user-configuration-od-remove-int', function() {
								if (jQuery(this).closest('td').index() > 3)
									jQuery(this).closest('td').prev().remove();
							});
					/* save od */
					jQuery('#user-configuration')
							.on(
									'click',
									'.user-configuration-od-save',
									function() {

										var postData = {
											times : [],
											vphs : []
										};
										var error = '';
										var tr = jQuery(this).closest('tr');
										tr
												.find('td.od-td')
												.each(
														function(i, element) {
															var time = jQuery(
																	element)
																	.find(
																			'input[name="time"]')
																	.val();
															var vph = jQuery(
																	element)
																	.find(
																			'input[name="vph"]')
																	.val();
															if (isNaN(vph)) {
																error = value
																		+ ' is not a number!';
																return false;
															}
															postData.times
																	.push(time);
															postData.vphs
																	.push(vph);
														});

										if (error) {
											simwebhelper.error(error);
											return;
										}
										postData.id = jQuery(this)
												.closest('td').attr('data-id');
										postData.destinatioId = tr.find(
												'td.od-td-destination select')
												.val();
										postData.vehicleCompositionName = tr
												.find(
														'td.od-td-vehiclecomposition select')
												.val();
										simwebhelper
												.action(
														'od/save',
														postData,
														function() {
															simwebhelper
																	.getStr(
																			'od/info/'
																					+ postData.id,
																			function(
																					html) {
																				tr
																						.replaceWith(html);
																			});
														});

									});
					/* create new od */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-od-new',
							function() {
								simwebhelper.action('od/new', {
									originId : jQuery(this).closest('td').attr(
											'data-id'),
									destinationId : 0
								}, function(data) {
									simwebhelper.getStr('od/form/' + data
											+ ";isNew=true", function(html) {
										jQuery('#user-configuration-ods-tbody')
												.append(html);
									});

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
								var tr = jQuery(this).closest('tr');
								simwebhelper.getStr('vehiclecomposition/form/'
										+ jQuery(this).closest('td').attr(
												'data-name'), function(html) {
									tr.replaceWith(html);
								});
							});
					/* cancel edit vehicle composition */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-vehicle-comp-cancel',
							function() {
								var tr = jQuery(this).closest('tr');
								if (jQuery(this).closest('td').attr(
										'data-is-new') === 'true') {
									simwebhelper.action(
											'vehiclecomposition/remove', {
												name : jQuery(this).closest(
														'td').attr('data-name')
											}, function() {
												tr.remove();
											});
								} else {
									simwebhelper.getStr(
											'vehiclecomposition/info/'
													+ jQuery(this)
															.closest('td')
															.attr('data-name'),
											function(html) {
												tr.replaceWith(html);
											});
								}
							});
					/* remove vehicle composition */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-vehicle-comp-remove',
							function() {
								var tr = jQuery(this).closest('tr');
								simwebhelper.action(
										'vehiclecomposition/remove', {
											name : jQuery(this).closest('td')
													.attr('data-name')
										}, function() {
											tr.remove();
										});
							});
					/* add vehicle type in vehicle composition */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-vehicle-comp-add',
							function() {
								jQuery(this).closest('td').before(
										jQuery(this).closest('td').prev()
												.clone());
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
										var tr = jQuery(this).closest('tr');
										tr
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
										postData.oldName = jQuery(this)
												.closest('td')
												.attr('data-name');
										postData.newName = tr
												.find(
														'td.vehiclecomposition-td-name input:text')
												.val();
										simwebhelper
												.action(
														'vehiclecomposition/save',
														postData,
														function() {
															simwebhelper
																	.getStr(
																			'vehiclecomposition/info/'
																					+ postData.newName,
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
														'vehiclecomposition/new',
														{},
														function(data) {
															simwebhelper
																	.getStr(
																			'vehiclecomposition/form/'
																					+ data
																					+ ";isNew=true",
																			function(
																					html) {
																				jQuery(
																						'#user-configuration-vehicle-compositions-tbody')
																						.append(
																								html);
																			});

														});
									});
					/***********************************************************
					 * Panel, User Configuration, Simulator
					 **********************************************************/
					/* run simulation */
					jQuery('#user-configuration').on('click',
							'.user-configuration-run-simulation', function() {
								simwebhelper.action("simulator/run");
								simwebhelper.hidePanel();
							});
					/***********************************************************
					 * Panel, User Configuration, Simulation Result
					 **********************************************************/
					/* start simulation */
					jQuery('#user-configuration').on('click',
							'.user-configuration-results-start-animation',
							function() {
								simwebhelper.hidePanel();
								that.startAnimation();
							});
					/* stop simulation */
					jQuery('#user-configuration').on('click',
							'.user-configuration-results-stop-animation',
							function() {
								that.stopAnimation();
							});
					/* draw trajectory */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-results-trajectory',
							function() {
								that.stopAnimation();
								simwebhelper.getStr('results/trajectory/'
										+ jQuery(this).closest('tr').find(
												'select').val(), function(trj) {
									that.drawTrajectory(trj);
								});
							});
					/* link stat plot */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-results-tsd-plot',
							function() {
								that.stopAnimation();
								simwebhelper.getJson('results/tsd/'
										+ jQuery(this).closest('tr').find(
												'select').val(),
										function(data) {
											simplot.plot(data);
										});
							});
					/* link stat close plot */
					jQuery('#user-configuration').on('click',
							'.user-configuration-results-tsd-close',
							function() {
								simplot.close();
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
									if (data['links'] != null
											|| data['nodes'] != null)
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
