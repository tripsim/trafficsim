package edu.trafficsim.engine.osm.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;

import edu.trafficsim.engine.Engine;

public class OsmParser {

	public static Highways parse(URL url) throws JsonParseException,
			ProtocolException, IOException, XMLStreamException {

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		String contentType = conn.getContentType().toLowerCase();
		if (contentType.contains("xml")) {
			return parseXml(conn.getInputStream());
		} else if (contentType.contains("json")) {
			return parseJson(conn.getInputStream());
		} else {
			throw new RuntimeException("Failed : Unknown content type");
		}
	}

	public static Highways parseJson(InputStream inputStream)
			throws JsonParseException, IOException {
		Highways highways = new Highways();
		JsonParser jsonParser = Engine.jsonFactory.createParser(inputStream);
		OsmJsonParser.parse(jsonParser, highways);
		return highways;
	}

	public static Highways parseXml(InputStream inputStream)
			throws XMLStreamException {
		Highways highways = new Highways();
		XMLStreamReader reader = Engine.xmlInputFactory
				.createXMLStreamReader(inputStream);
		OsmXmlParser.parse(reader, highways);
		return highways;
	}
}
