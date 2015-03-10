package org.tripsim.engine.network.osm;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;

import javax.xml.stream.XMLStreamException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tripsim.engine.demo.DemoBuilder;
import org.tripsim.engine.network.osm.Highways;
import org.tripsim.engine.network.osm.OsmNetworkExtractor;
import org.tripsim.engine.network.osm.OsmParser;

import com.fasterxml.jackson.core.JsonParseException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simengine-test.xml")
public class OSMExtractorTest {

	String name = "Test";
	@Autowired
	OsmNetworkExtractor extractor;

	@Ignore
	@Test
	public void testExtractByUrl() throws JsonParseException,
			ProtocolException, IOException, XMLStreamException {
		String urlPre = "http://api.openstreetmap.fr/xapi?";
		String testQuery = "way[highway=*][bbox=-89.4114,43.0707,-89.3955,43.0753]";
		extractor.extract(urlPre + testQuery, name);
	}

	@Ignore
	@Test
	public void testParseXml() throws JsonParseException, IOException,
			XMLStreamException {
		InputStream in = DemoBuilder.class.getResourceAsStream("demo.xml");
		@SuppressWarnings("unused")
		Highways highways = OsmParser.parseXml(in);

	}

	@Ignore
	@Test
	public void testParseJson() throws JsonParseException, IOException {
		InputStream in = DemoBuilder.class.getResourceAsStream("demo.json");
		@SuppressWarnings("unused")
		Highways highways = OsmParser.parseJson(in);
	}

}
