package org.tripsim.engine.network.osm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OSMXAPITest {

	public static void main(String[] args) {
		test();
	}

	protected static void test() {
		// String urlPre = "http://jxapi.openstreetmap.org/xapi/api/0.6/";
		// String urlPre = "http://open.mapquestapi.com/xapi/api/0.6/";
		// String urlPre = "http://overpass.osm.rambler.ru/cgi/xapi?";
		// String urlPre = "http://www.overpass-api.de/api/xapi?";
		String urlPre = "http://api.openstreetmap.fr/xapi?";
		String testQuery = "way[highway=*][bbox=-89.4114,43.0707,-89.3955,43.0753]";

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

			System.out.println(conn.getRequestProperty("Accept"));
			System.out.println(conn.getContentType());

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
