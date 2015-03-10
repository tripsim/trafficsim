package org.tripsim.engine.network;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tripsim.api.model.Network;
import org.tripsim.data.dom.NetworkDo;
import org.tripsim.engine.demo.DemoBuilder;
import org.tripsim.engine.network.NetworkConverter;

import com.vividsolutions.jts.io.ParseException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simengine-test.xml")
public class NetworkConverterTest {

	Logger logger = LoggerFactory.getLogger(NetworkConverterTest.class);
	@Autowired
	DemoBuilder demoBuilder;

	@Autowired
	NetworkConverter converter;

	@Test
	public void test() throws ParseException {
		Network network = demoBuilder.getDemo().getNetwork();
		NetworkDo entity = converter.toNetworkDo(network);
		logger.info("{}", entity);
		Network newNetwork = converter.toNetwork(entity);
		logger.info("{}", newNetwork);
	}

}
