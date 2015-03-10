/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.engine.network.osm;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.tripsim.util.JsonSerializer;
import org.tripsim.util.XmlUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;

/**
 * 
 * 
 * @author Xuan Shi
 */
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
		JsonParser jsonParser = JsonSerializer.createJsonParser(inputStream);
		OsmJsonParser.parse(jsonParser, highways);
		return highways;
	}

	public static Highways parseXml(InputStream inputStream)
			throws XMLStreamException {
		Highways highways = new Highways();
		XMLStreamReader reader = XmlUtils.createStreamReader(inputStream);
		OsmXmlParser.parse(reader, highways);
		return highways;
	}
}
