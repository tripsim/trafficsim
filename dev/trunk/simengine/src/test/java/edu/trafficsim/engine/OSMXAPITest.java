package edu.trafficsim.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.opengis.referencing.operation.TransformException;

import com.fasterxml.jackson.core.JsonParseException;

import edu.trafficsim.engine.factory.DefaultNetworkFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.engine.osm.Highways;
import edu.trafficsim.engine.osm.HighwaysJsonParser;
import edu.trafficsim.engine.osm.OsmNetworkExtractor;
import edu.trafficsim.model.core.ModelInputException;

public class OSMXAPITest {

	public static void main(String[] args) throws TransformException {
		 test();
		// testParse();
		// testExtractByReader();
//		testExtractByUrl();
	}

	static Sequence seq = Sequence.create();
	static NetworkFactory networkFactory = DefaultNetworkFactory.getInstance();
	static TypesLibrary typesLibrary = null;
	static OsmNetworkExtractor extractor = new OsmNetworkExtractor(seq);
	static {
		try {
			typesLibrary = TypesLibrary.defaultLibrary();
		} catch (ModelInputException e) {
		}
	}

	protected static void testExtractByReader() throws TransformException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				OSMXAPITest.class.getResourceAsStream("test.json")));

		try {
			extractor.extract(extractor.parse(reader), typesLibrary,
					networkFactory);

		} catch (ModelInputException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected static void testExtractByUrl() throws TransformException {
		String urlPre = "http://jxapi.openstreetmap.org/xapi/api/0.6";
		String testQuery = "/way[highway=*][bbox=-89.4114,43.0707,-89.3955,43.0753]";
		try {
			extractor.extract(urlPre + testQuery, typesLibrary, networkFactory);
		} catch (ModelInputException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected static void testParse() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				OSMXAPITest.class.getResourceAsStream("test.json")));
		HighwaysJsonParser parser = new HighwaysJsonParser();
		try {
			parser.parse(reader);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		Highways highways = parser.getParsedHighways();
	}

	protected static void test() {
//		String urlPre = "http://jxapi.openstreetmap.org/xapi/api/0.6";
		String urlPre = "http://open.mapquestapi.com/xapi/api/0.6";
		String testQuery = "/way[highway=*][bbox=-89.4114,43.0707,-89.3955,43.0753]";

		String urlStr = urlPre + testQuery;
		try {

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			// conn.setRequestProperty("Accept", "text/*");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
