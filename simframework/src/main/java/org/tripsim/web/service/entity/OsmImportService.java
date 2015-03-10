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
package org.tripsim.web.service.entity;

import java.io.IOException;
import java.net.ProtocolException;

import javax.xml.stream.XMLStreamException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tripsim.api.model.Network;
import org.tripsim.engine.network.NetworkExtractResult;
import org.tripsim.engine.network.NetworkExtractor;
import org.tripsim.web.Sequence;

import com.fasterxml.jackson.core.JsonParseException;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service("osm-import-service")
public class OsmImportService extends EntityService {

	// "way[highway=*][bbox=-89.4114,43.0707,-89.3955,43.0753]"
	private static final String queryTemplate = "way[highway=%s][bbox=%s]";
	private static final String newNetworkName = "New Network";

	@Autowired
	NetworkExtractor extractor;

	public Network createNetwork(String url, String bbox, String highway,
			Sequence sequence) throws JsonParseException, ProtocolException,
			IOException, XMLStreamException {

		String highwayQuery = OsmHighwayValue.valueOf(highway) == null ? OsmHighwayValue.All.queryValue
				: OsmHighwayValue.valueOf(highway).queryValue;
		String query = String.format(queryTemplate, highwayQuery, bbox);
		NetworkExtractResult networkExtractResult = extractor.extract(url
				+ query, newNetworkName);
		sequence.reset(networkExtractResult.getEndId());
		return networkExtractResult.getNetwork();
	}

	/**
	 * 
	 * 
	 * @author Xuan Shi
	 */
	public static enum OsmXapiUrl {
		jXapi("http://jxapi.openstreetmap.org/xapi/api/0.6/"), MapQuest(
				"http://open.mapquestapi.com/xapi/api/0.6/"), OverPassRambler(
				"http://overpass.osm.rambler.ru/cgi/xapi?"), OverPassDe(
				"http://www.overpass-api.de/api/xapi?"), OverPassFr(
				"http://api.openstreetmap.fr/xapi?");

		private final String url;

		OsmXapiUrl(String url) {
			this.url = url;
		}

		public String getUrl() {
			return url;
		}
	}

	/**
	 * 
	 * 
	 * @author Xuan Shi
	 */
	public static enum OsmHighwayValue {
		Motorway("Motorway only", "motorway|motorway_link"), Trunk(
				"Trunk and above", "motorway|motorway_link|trunk|trunk_link"), Primary(
				"Primary Highway and above",
				"motorway|motorway_link|trunk|trunk_link|primary|primary_link"), Secondary(
				"Secondary Highway and above",
				"motorway|motorway_link|trunk|trunk_link|primary|primary_link|secondary|secondary_link"), Tertiary(
				"Tertiary Highway and above",
				"motorway|motorway_link|trunk|trunk_link|primary|primary_link|secondary|secondary_link|tertiary"), Residential(
				"Residential and unclassified roads and above",
				"motorway|motorway_link|trunk|trunk_link|primary|primary_link|secondary|secondary_link|tertiary|residential|unclassified"), All(
				"All roads", "*");

		private final String desc;
		private final String queryValue;

		OsmHighwayValue(String desc, String queryValue) {
			this.desc = desc;
			this.queryValue = queryValue;
		}

		public String getName() {
			return name();
		}

		public String getDesc() {
			return desc;
		}
	}

}
