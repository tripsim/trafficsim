package edu.trafficsim.engine.osm;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.MappingJsonFactory;

abstract class AbstractHighwaysJsonParser {

	private static JsonFactory jsonFactory = null;

	AbstractHighwaysJsonParser() {
		if (jsonFactory == null)
			jsonFactory = new MappingJsonFactory();
	}

	public void parse(Reader reader) throws JsonParseException, IOException {
		JsonParser jsonParser = jsonFactory.createParser(reader);
		parse(jsonParser);
	}

	public void parse(URL url) throws JsonParseException, ProtocolException,
			IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		parse(conn.getInputStream());
	}

	public void parse(InputStream inputStream) throws JsonParseException,
			IOException {
		JsonParser jsonParser = jsonFactory.createParser(inputStream);
		parse(jsonParser);
	}

	protected void parse(JsonParser jsonParser) throws JsonParseException,
			IOException {
		jsonParser.nextToken(); // JsonToken.START_OBJECT
		while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
			String key = jsonParser.getCurrentName();
			if ("nodes".equals(key)) {
				parseNodes(jsonParser);
			} else if ("ways".equals(key)) {
				parseWays(jsonParser);
				return;
			} else if ("relations".equals(key)) {
				parseRelations(jsonParser);
			} else {
				jsonParser.skipChildren();
				// SKIPPED keys
				// version generator copyright attribution license
				// ALTERNATIONS
				// overpass api use "elements" to hold node, way, relation
				// together
			}
		}
		jsonParser.close();
	}

	abstract protected void parseNodes(JsonParser jsonParser)
			throws JsonParseException, IOException;

	abstract protected void parseWays(JsonParser jsonParser)
			throws JsonParseException, IOException;

	abstract protected void parseRelations(JsonParser jsonParser)
			throws JsonParseException, IOException;
}
