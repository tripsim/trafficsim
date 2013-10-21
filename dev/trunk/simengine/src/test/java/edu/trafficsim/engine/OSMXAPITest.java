package edu.trafficsim.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import edu.trafficsim.engine.osm.Highways;
import edu.trafficsim.engine.osm.HighwaysJsonParser;

public class OSMXAPITest {

	public static void main(String[] args) {

		String url = "http://jxapi.openstreetmap.org/xapi/api/0.6";
		String testQuery = "/way[highway=*][bbox=-89.4114,43.0707,-89.3955,43.0753]";

		//test(url+testQuery);
		testParse();
	}

	protected static void testParse() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				OSMXAPITest.class.getResourceAsStream("test.json")));
		HighwaysJsonParser parser = new HighwaysJsonParser();
		parser.parse(reader);
		Highways highways = parser.getParsedHighways();
	}

	protected static void test(String urlStr) {
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
