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
package org.tripsim.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.tripsim.api.model.TypesComposition;
import org.tripsim.api.model.VehicleClass;
import org.tripsim.data.dom.ElementTypeDo;
import org.tripsim.data.dom.TypeCategoryDo;
import org.tripsim.data.persistence.CompositionDao;
import org.tripsim.data.persistence.ElementTypeDao;
import org.tripsim.engine.type.DriverType;
import org.tripsim.engine.type.LinkType;
import org.tripsim.engine.type.NodeType;
import org.tripsim.engine.type.TypesFactory;
import org.tripsim.engine.type.TypesManager;
import org.tripsim.engine.type.VehicleType;

@Component
public class MongoDefaultPopulator implements
		ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LoggerFactory
			.getLogger(MongoDefaultPopulator.class);

	private static final String CAR = "Default Car";
	private static final String TRUCK = "Default Truck";
	private static final String DRIVER = "Default Driver";
	private static final String LINK = "Default Link";
	private static final String NODE = "Default Node";

	private static final String VEHICLE_COMPO = "Default Vehicle Composition";
	private static final String DRIVER_COMPO = "Default Driver Composition";

	@Autowired
	ElementTypeDao elementTypeDao;
	@Autowired
	CompositionDao compositionDao;

	@Autowired
	TypesManager typesManager;
	@Autowired
	TypesFactory typesFactory;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("Populating default values to mongo db!");
		populateVehicleTypes();
		populateDriverTypes();
		populateLinkypes();
		populateNodeTypes();
		populateVehicleCompositions();
		populateDriverCompositions();
	}

	private void populateVehicleTypes() {
		if (elementTypeDao.getDefaultByCategory(TypeCategoryDo.VEHICLE_TYPE) == null) {
			logger.info("Populating default vehicle type(s) to mongo db!");
			VehicleType type = typesManager.getVehicleType(CAR);
			if (type == null) {
				type = typesFactory.createVehicleType(CAR, VehicleClass.Car);
			}
			type.setDefault(true);
			typesManager.saveVehicleType(type);

			type = typesManager.getVehicleType(TRUCK);
			if (type == null) {
				type = typesFactory
						.createVehicleType(TRUCK, VehicleClass.Truck);
			}
			type.setDefault(true);
			typesManager.saveVehicleType(type);
		}
	}

	private void populateDriverTypes() {
		if (elementTypeDao.getDefaultByCategory(TypeCategoryDo.DRIVER_TYPE) == null) {
			logger.info("Populating default driver type(s) to mongo db!");
			DriverType type = typesManager.getDriverType(DRIVER);
			if (type == null) {
				type = typesFactory.createDriverType(DRIVER);
			}
			type.setDefault(true);
			typesManager.saveDriverType(type);
		}
	}

	private void populateLinkypes() {
		if (elementTypeDao.getDefaultByCategory(TypeCategoryDo.LINK_TYPE) == null) {
			logger.info("Populating default link type(s) to mongo db!");
			LinkType type = typesManager.getLinkType(LINK);
			if (type == null) {
				type = typesFactory.createLinkType(LINK);
			}
			type.setDefault(true);
			typesManager.saveLinkType(type);
		}
	}

	private void populateNodeTypes() {
		if (elementTypeDao.getDefaultByCategory(TypeCategoryDo.NODE_TYPE) == null) {
			logger.info("Populating default node type(s) to mongo db!");
			NodeType type = typesManager.getNodeType(NODE);
			if (type == null) {
				type = typesFactory.createNodeType(NODE);
			}
			type.setDefault(true);
			typesManager.saveNodeType(type);
		}
	}

	private void populateVehicleCompositions() {
		if (compositionDao.getDefaultByCategory(TypeCategoryDo.VEHICLE_TYPE) == null) {
			logger.info("Populating default vehicle composition(s) to mongo db!");
			ElementTypeDo type1 = elementTypeDao.getByName(
					TypeCategoryDo.VEHICLE_TYPE, CAR);
			ElementTypeDo type2 = elementTypeDao.getByName(
					TypeCategoryDo.VEHICLE_TYPE, TRUCK);

			TypesComposition composition;
			if (type1 == null || type2 == null) {
				type1 = elementTypeDao
						.getDefaultByCategory(TypeCategoryDo.VEHICLE_TYPE);
				composition = typesFactory.createTypesComposition(
						VEHICLE_COMPO, new String[] { type1.getName() },
						new Double[] { 1.0 });
			} else {
				composition = typesFactory.createTypesComposition(
						VEHICLE_COMPO,
						new String[] { type1.getName(), type2.getName() },
						new Double[] { 0.2, 0.8 });
			}
			composition.setDefault(true);
			typesManager.insertVehicleTypesComposition(composition);
		}
	}

	private void populateDriverCompositions() {
		if (compositionDao.getDefaultByCategory(TypeCategoryDo.DRIVER_TYPE) == null) {
			logger.info("Populating default driver composition(s) to mongo db!");
			ElementTypeDo type = elementTypeDao.getByName(
					TypeCategoryDo.DRIVER_TYPE, DRIVER);
			if (type == null) {
				type = elementTypeDao
						.getDefaultByCategory(TypeCategoryDo.DRIVER_TYPE);
			}
			TypesComposition composition = typesFactory.createTypesComposition(
					DRIVER_COMPO, new String[] { type.getName() },
					new Double[] { 1.0 });
			composition.setDefault(true);
			typesManager.insertDriverTypesComposition(composition);
		}
	}

}