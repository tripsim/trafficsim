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
jQuery(document)
		.ready(
				function() {
					/***********************************************************
					 * Panel, User Configuration, Vehicle Composition Edit
					 **********************************************************/
					/**
					 * buttons for info
					 */
					/* edit composition */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-composition-edit',
							function() {
								var tr = jQuery(this).closest('tr');
								simwebhelper.fillHtml('compositions/'
										+ tr.attr('data-type') + '/form/'
										+ tr.attr('data-name'), tr);
							});

					/* remove composition */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-composition-remove',
							function() {
								var tr = jQuery(this).closest('tr');
								simwebhelper.action('compositions/'
										+ tr.attr('data-type') + '/remove', {
									name : tr.attr('data-name')
								}, function() {
									tr.remove();
								});
							});
					/**
					 * buttons for form
					 */
					/* cancel edit composition */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-composition-cancel',
							function() {
								var tr = jQuery(this).closest('tr');
								if (tr.attr('data-is-new') === 'true') {
									simwebhelper.action('compositions/'
											+ tr.attr('data-type') + '/remove',
											{
												name : tr.attr('data-name')
											}, function() {
												tr.remove();
											});
								} else {
									simwebhelper.fillHtml('compositions/'
											+ tr.attr('data-type') + '/info/'
											+ tr.attr('data-name'), tr);
								}
							});
					/* add type in composition */
					jQuery('#user-configuration').on(
							'click',
							'.user-configuration-composition-add',
							function() {
								jQuery(this).closest('td').before(
										jQuery(this).closest('td').prev()
												.clone());
							});
					/* save composition */
					jQuery('#user-configuration')
							.on(
									'click',
									'.user-configuration-composition-save',
									function() {
										var postData = {
											types : [],
											values : []
										};
										var error = '';
										var tr = jQuery(this).closest('tr');
										tr
												.find('td.composition-td')
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
																			'input')
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
											return;
										}
										postData.oldName = tr.attr('data-name');
										postData.newName = tr.find(
												'input[name="name"]').val();
										simwebhelper
												.action(
														'compositions/'
																+ tr
																		.attr('data-type')
																+ '/save',
														postData,
														function() {
															simwebhelper
																	.fillHtml(
																			'compositions/'
																					+ tr
																							.attr('data-type')
																					+ '/info/'
																					+ postData.newName,
																			tr);
														});
									});
					/**
					 * buttons for view
					 */
					/* create new vehicle composition */
					jQuery('#user-configuration')
							.on(
									'click',
									'.user-configuration-vehicle-composition-new',
									function() {
										simwebhelper
												.action(
														'compositions/vehicle/new',
														{},
														function(data) {
															simwebhelper
																	.getStr(
																			'compositions/vehicle/form/'
																					+ data
																					+ ";isNew=true",
																			function(
																					html) {
																				jQuery(
																						'#user-configuration-vehicle-compositions')
																						.append(
																								html);
																			});

														});
									});
					/* create new driver composition */
					jQuery('#user-configuration')
							.on(
									'click',
									'.user-configuration-driver-composition-new',
									function() {
										simwebhelper
												.action(
														'compositions/driver/new',
														{},
														function(data) {
															simwebhelper
																	.getStr(
																			'compositions/driver/form/'
																					+ data
																					+ ";isNew=true",
																			function(
																					html) {
																				jQuery(
																						'#user-configuration-driver-compositions')
																						.append(
																								html);
																			});

														});
									});
				});