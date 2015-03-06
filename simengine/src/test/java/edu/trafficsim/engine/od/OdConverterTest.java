package edu.trafficsim.engine.od;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.trafficsim.api.model.OdMatrix;
import edu.trafficsim.data.dom.OdMatrixDo;
import edu.trafficsim.engine.demo.DemoBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simengine-test.xml")
public class OdConverterTest {

	Logger logger = LoggerFactory.getLogger(OdConverterTest.class);
	@Autowired
	DemoBuilder demoBuilder;
	@Autowired
	OdConverter converter;

	@Test
	public void test() {
		OdMatrix odMatrix = demoBuilder.getDemo().getOdMatrix();
		OdMatrixDo entity = converter.toOdMatrixDo(odMatrix);
		logger.info("{}", entity);
		OdMatrix newOdMatrix = converter.toOdMatrix(entity);
		logger.info("{}", newOdMatrix);
	}

}
