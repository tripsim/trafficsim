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
package edu.trafficsim.utility;

import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * 
 * 
 * @author Xuan Shi
 */
public final class XmlUtils {

	private final XMLInputFactory xmlInputFactory;

	private static class XmlFactoryHolder {
		private static XmlUtils holder = new XmlUtils();
	}

	private XmlUtils() {
		xmlInputFactory = XMLInputFactory.newInstance();
	}

	public static XMLStreamReader createStreamReader(InputStream inputStream)
			throws XMLStreamException {
		return XmlFactoryHolder.holder.xmlInputFactory
				.createXMLStreamReader(inputStream);
	}
}
