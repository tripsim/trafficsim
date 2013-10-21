package edu.trafficsim.engine.osm;

import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import edu.trafficsim.model.Network;

public class OsmNetworkExtractor {

	public static Network extract(Reader reader) {
		HighwaysJsonParser parser = new HighwaysJsonParser();
		parser.parse(reader);
		return getNetwork(parser.getParsedHighways());
	}

	public static Network extract(String urlStr) throws MalformedURLException {
		HighwaysJsonParser parser = new HighwaysJsonParser();
		URL url = new URL(urlStr);
		parser.parse(url);
		return getNetwork(parser.getParsedHighways());
	}

	protected static Network getNetwork(Highways highways) {

		return null;
	}
}
