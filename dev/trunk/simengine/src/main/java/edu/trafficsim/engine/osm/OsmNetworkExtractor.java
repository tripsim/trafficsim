/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.engine.osm;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;

import org.opengis.referencing.operation.TransformException;

import com.fasterxml.jackson.core.JsonParseException;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.engine.osm.parser.Highways;
import edu.trafficsim.engine.osm.parser.NetworkCreator;
import edu.trafficsim.engine.osm.parser.OsmParser;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.core.ModelInputException;

/**
 * 
 * 
 * @author Xuan Shi
 */
public class OsmNetworkExtractor {

	public static Highways parseJson(InputStream in) throws JsonParseException,
			IOException {
		return OsmParser.parseJson(in);
	}

	public static Highways parseXml(InputStream in) throws XMLStreamException {
		return OsmParser.parseXml(in);
	}

	public static Highways parse(String urlStr) throws ModelInputException,
			JsonParseException, IOException, XMLStreamException {
		URL url = new URL(urlStr);
		return OsmParser.parse(url);
	}

	public static Network extract(String urlStr, TypesLibrary typesLibrary,
			NetworkFactory networkFactory, Sequence seq, String name)
			throws ModelInputException, JsonParseException, ProtocolException,
			IOException, TransformException, XMLStreamException {
		Highways highways = parse(urlStr);
		return extract(highways, typesLibrary, networkFactory, seq, name);
	}

	public static Network extract(Highways highways, TypesLibrary typesLibrary,
			NetworkFactory networkFactory, Sequence seq, String name)
			throws ModelInputException, JsonParseException, ProtocolException,
			IOException, TransformException {
		return NetworkCreator.createNetwork(highways, typesLibrary,
				networkFactory, seq, name);
	}

}
