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
package org.tripsim.data.persistence.impl;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tripsim.data.dom.CompositionDo;
import org.tripsim.data.dom.TypeCategoryDo;
import org.tripsim.data.persistence.CompositionDao;

public class CompositionDaoTest extends AbstractMongoTest {

	@Autowired
	CompositionDao compositionDao;

	String compositionName = "composition";
	String vehicleType = "Vehicle_Test";

	@Before
	public void setup() {
		dropDb("compositions");

		CompositionDo compositionDo = new CompositionDo();
		compositionDo.setName(compositionName);
		compositionDo.setDefaultComposition(false);
		compositionDo.setCategory(TypeCategoryDo.VEHICLE_TYPE);
		Map<String, Double> composition = new HashMap<String, Double>();
		compositionDo.setComposition(composition);
		composition.put(vehicleType, 0.12345);
		compositionDao.save(compositionDo);

		compositionDo = new CompositionDo();
		compositionDo.setName(compositionName + "_Test");
		compositionDo.setDefaultComposition(false);
		compositionDo.setCategory(TypeCategoryDo.VEHICLE_TYPE);
		composition = new HashMap<String, Double>();
		compositionDo.setComposition(composition);
		composition.put(vehicleType, 0.12345);
		compositionDao.save(compositionDo);
	}

	@Test
	public void testCountByType() {
		long count = compositionDao.countByType(TypeCategoryDo.VEHICLE_TYPE,
				vehicleType);
		assertEquals(2, count);
		count = compositionDao.countByType(TypeCategoryDo.DRIVER_TYPE,
				vehicleType);
		assertEquals(0, count);
		count = compositionDao.countByType(TypeCategoryDo.VEHICLE_TYPE,
				vehicleType + "1");
		assertEquals(0, count);
	}

}
