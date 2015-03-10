package edu.trafficsim.data.persistence.impl;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.trafficsim.data.dom.CompositionDo;
import edu.trafficsim.data.dom.TypeCategoryDo;
import edu.trafficsim.data.persistence.CompositionDao;

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
