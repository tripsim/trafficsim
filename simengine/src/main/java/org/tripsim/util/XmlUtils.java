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
package org.tripsim.util;

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
