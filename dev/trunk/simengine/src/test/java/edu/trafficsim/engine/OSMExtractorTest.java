package edu.trafficsim.engine;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;

import javax.xml.stream.XMLStreamException;

import org.opengis.referencing.operation.TransformException;

import com.fasterxml.jackson.core.JsonParseException;

import edu.trafficsim.engine.demo.DemoBuilder;
import edu.trafficsim.engine.factory.DefaultNetworkFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.engine.osm.OsmNetworkExtractor;
import edu.trafficsim.engine.osm.parser.Highways;
import edu.trafficsim.engine.osm.parser.OsmParser;
import edu.trafficsim.model.core.ModelInputException;

public class OSMExtractorTest {

	public static void main(String[] args) throws TransformException,
			JsonParseException, ProtocolException, ModelInputException,
			IOException, XMLStreamException {
		// testExtractByUrl();
		testParseXml();
		testParseJson();
	}

	static Sequence seq = Sequence.create();
	static String name = "Test";
	static NetworkFactory networkFactory = DefaultNetworkFactory.getInstance();
	static TypesLibrary typesLibrary = null;

	static {
		try {
			typesLibrary = TypesLibrary.defaultLibrary();
		} catch (ModelInputException e) {
			e.printStackTrace();
		}
	}

	protected static void testExtractByUrl() throws TransformException,
			JsonParseException, ProtocolException, ModelInputException,
			IOException, XMLStreamException {
		String urlPre = "http://api.openstreetmap.fr/xapi?";
		String testQuery = "way[highway=*][bbox=-89.4114,43.0707,-89.3955,43.0753]";
		OsmNetworkExtractor.extract(urlPre + testQuery, typesLibrary,
				networkFactory, seq, name);
	}

	protected static void testParseXml() throws TransformException,
			JsonParseException, IOException, XMLStreamException {
		InputStream in = DemoBuilder.class.getResourceAsStream("demo.xml");
		@SuppressWarnings("unused")
		Highways highways = OsmParser.parseXml(in);

	}

	protected static void testParseJson() throws JsonParseException,
			IOException {
		InputStream in = DemoBuilder.class.getResourceAsStream("demo.json");
		@SuppressWarnings("unused")
		Highways highways = OsmParser.parseJson(in);
	}
}
