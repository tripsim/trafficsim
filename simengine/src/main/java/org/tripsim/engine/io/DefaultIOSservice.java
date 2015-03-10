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
package org.tripsim.engine.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tripsim.engine.network.NetworkFactory;
import org.tripsim.engine.od.OdFactory;
import org.tripsim.engine.simulation.SimulationProject;
import org.tripsim.engine.type.TypesFactory;
import org.tripsim.engine.type.TypesManager;

import com.vividsolutions.jts.io.ParseException;

@Service("default-io-service")
class DefaultIOSservice implements IOService {

	private final static Logger logger = LoggerFactory
			.getLogger(DefaultIOSservice.class);

	@Autowired
	NetworkFactory networkFactory;
	@Autowired
	OdFactory odFactory;
	@Autowired
	TypesFactory typesFactory;
	@Autowired
	TypesManager typesManager;

	@Override
	public void exportProject(SimulationProject project, OutputStream out) {
		try {
			ProjectJsonExporter.exportProject(project, out);
		} catch (IOException e) {
			logger.error("failed to export from source!");
		}

	}

	@Override
	public SimulationProject importProject(InputStream in) {
		try {
			return ProjectJsonImporter.importProject(in, networkFactory,
					odFactory, typesFactory, typesManager);
		} catch (IOException | ParseException e) {
			logger.error("failed to import from source!", e);
		}
		return null;
	}
}
