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
package edu.trafficsim.engine.network.osm;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;

import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;

import edu.trafficsim.engine.network.NetworkExtractResult;
import edu.trafficsim.engine.network.NetworkExtractor;
import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.model.core.ModelInputException;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service("osm-network-extractor")
class OsmNetworkExtractor implements NetworkExtractor {

	private static final Logger logger = LoggerFactory
			.getLogger(OsmNetworkExtractor.class);

	@Autowired
	TypesManager typesManager;
	@Autowired
	NetworkFactory networkFactory;

	public Highways parseJson(InputStream in) throws JsonParseException,
			IOException {
		return OsmParser.parseJson(in);
	}

	public Highways parseXml(InputStream in) throws XMLStreamException {
		return OsmParser.parseXml(in);
	}

	public Highways parse(String urlStr) throws ModelInputException,
			JsonParseException, IOException, XMLStreamException {
		URL url = new URL(urlStr);
		return OsmParser.parse(url);
	}

	@Override
	public NetworkExtractResult extract(String urlStr, String name) {
		Highways highways;
		try {
			highways = parse(urlStr);
			return extract(highways, name);
		} catch (ModelInputException | IOException | XMLStreamException
				| TransformException e) {
			logger.error("failed to create network");
			throw new RuntimeException(e);
		}
	}

	public NetworkExtractResult extract(Highways highways, String name)
			throws ModelInputException, JsonParseException, ProtocolException,
			IOException, TransformException {
		return NetworkCreator.createNetwork(highways, typesManager,
				networkFactory, name);
	}

}
