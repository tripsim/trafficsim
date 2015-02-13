package edu.trafficsim.engine.network.osm;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;

import javax.xml.stream.XMLStreamException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;

import edu.trafficsim.engine.demo.DemoBuilder;
import edu.trafficsim.engine.network.osm.Highways;
import edu.trafficsim.engine.network.osm.OsmNetworkExtractor;
import edu.trafficsim.engine.network.osm.OsmParser;
import edu.trafficsim.model.core.ModelInputException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simengine-test.xml")
public class OSMExtractorTest {

	String name = "Test";
	@Autowired
	OsmNetworkExtractor extractor;

	@Ignore
	@Test
	protected void testExtractByUrl() throws TransformException,
			JsonParseException, ProtocolException, ModelInputException,
			IOException, XMLStreamException {
		String urlPre = "http://api.openstreetmap.fr/xapi?";
		String testQuery = "way[highway=*][bbox=-89.4114,43.0707,-89.3955,43.0753]";
		extractor.extract(urlPre + testQuery, name);
	}

	@Ignore
	@Test
	protected void testParseXml() throws TransformException,
			JsonParseException, IOException, XMLStreamException {
		InputStream in = DemoBuilder.class.getResourceAsStream("demo.xml");
		@SuppressWarnings("unused")
		Highways highways = OsmParser.parseXml(in);

	}

	@Ignore
	@Test
	protected void testParseJson() throws JsonParseException, IOException {
		InputStream in = DemoBuilder.class.getResourceAsStream("demo.json");
		@SuppressWarnings("unused")
		Highways highways = OsmParser.parseJson(in);
	}

}
