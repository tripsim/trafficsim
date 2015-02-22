package edu.trafficsim.engine.network;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vividsolutions.jts.io.ParseException;

import edu.trafficsim.data.dom.NetworkDo;
import edu.trafficsim.engine.demo.DemoBuilder;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.core.ModelInputException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simengine-test.xml")
public class NetworkConverterTest {

	Logger logger = LoggerFactory.getLogger(NetworkConverterTest.class);
	@Autowired
	DemoBuilder demoBuilder;

	@Autowired
	NetworkConverter converter;

	@Test
	public void test() throws ParseException, ModelInputException {
		Network network = demoBuilder.getDemo().getNetwork();
		NetworkDo entity = converter.toNetworkDo(network);
		logger.info("{}", entity);
		Network newNetwork = converter.toNetwork(entity);
		logger.info("{}", newNetwork);
	}

}
