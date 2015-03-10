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
package org.tripsim.engine.network;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tripsim.api.model.Network;
import org.tripsim.data.dom.NetworkDo;
import org.tripsim.data.persistence.NetworkDao;

import com.vividsolutions.jts.io.ParseException;

@Service("default-network-manager")
class DefaultNetworkManager implements NetworkManager {

	private final static Logger logger = LoggerFactory
			.getLogger(DefaultNetworkManager.class);

	@Autowired
	NetworkDao networkDao;
	@Autowired
	NetworkConverter converter;

	@Override
	public void insertNetwork(Network network) {
		if (network == null) {
			throw new RuntimeException(
					"Network is null, cannot be saved to db!");
		}
		if (networkDao.countByName(network.getName()) != 0) {
			logger.info("Network '{}' already exists in db!", network.getName());

		}
		NetworkDo entity = converter.toNetworkDo(network);
		networkDao.save(entity);
	}

	@Override
	public void saveNetwork(Network network) {
		if (network == null) {
			throw new RuntimeException(
					"Network is null, cannot be saved to db!");
		}
		NetworkDo entity = networkDao.findByName(network.getName());
		if (entity == null) {
			entity = converter.toNetworkDo(network);
		} else {
			converter.applyNetworkDo(entity, network);
		}
		networkDao.save(entity);
	}

	@Override
	public Network loadNetwork(String name) {
		NetworkDo network = networkDao.findByName(name);
		if (network == null) {
			return null;
		}
		try {
			return converter.toNetwork(network);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getNetworkNames() {
		return (List<String>) networkDao.getTypeField("name");
	}

}
