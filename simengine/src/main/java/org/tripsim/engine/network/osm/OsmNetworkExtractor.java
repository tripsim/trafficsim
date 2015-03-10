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
package org.tripsim.engine.network.osm;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tripsim.engine.network.NetworkExtractResult;
import org.tripsim.engine.network.NetworkExtractor;
import org.tripsim.engine.network.NetworkFactory;
import org.tripsim.engine.type.TypesManager;

import com.fasterxml.jackson.core.JsonParseException;

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

	public Highways parse(String urlStr) throws JsonParseException,
			IOException, XMLStreamException {
		URL url = new URL(urlStr);
		return OsmParser.parse(url);
	}

	@Override
	public NetworkExtractResult extract(String urlStr, String name) {
		try {
			Highways highways = parse(urlStr);
			return extract(highways, name);
		} catch (IOException | XMLStreamException e) {
			logger.error("failed to create network");
			throw new RuntimeException(e);
		}
	}

	public NetworkExtractResult extract(Highways highways, String name)
			throws JsonParseException, ProtocolException, IOException {
		return NetworkCreator.createNetwork(highways, typesManager,
				networkFactory, name);
	}

	@Override
	public NetworkExtractResult extractByXml(InputStream input, String name) {
		try {
			Highways highways = parseXml(input);
			return extract(highways, name);
		} catch (IOException | XMLStreamException e) {
			logger.error("failed to create network");
			throw new RuntimeException(e);
		}
	}

	@Override
	public NetworkExtractResult extractByJson(InputStream input, String name) {
		try {
			Highways highways = parseJson(input);
			return extract(highways, name);
		} catch (IOException e) {
			logger.error("failed to create network");
			throw new RuntimeException(e);
		}
	}
}
