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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tripsim.engine.io.ProjectJsonExporter;
import org.tripsim.engine.io.ProjectJsonImporter;
import org.tripsim.engine.network.NetworkFactory;
import org.tripsim.engine.od.OdFactory;
import org.tripsim.engine.simulation.SimulationProject;
import org.tripsim.engine.type.TypesFactory;
import org.tripsim.engine.type.TypesManager;

import com.vividsolutions.jts.io.ParseException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simengine-test.xml")
public class ProjectImportExportTest {
	@Autowired
	NetworkFactory networkFactory;
	@Autowired
	OdFactory odFactory;
	@Autowired
	TypesFactory typesFactory;
	@Autowired
	TypesManager typesManager;

	@Ignore
	@Test
	public void testExport() throws IOException, ParseException {
		FileInputStream in = new FileInputStream(new File(
				"/Users/Xuan/Desktop/test.json"));
		SimulationProject project = ProjectJsonImporter.importProject(in,
				networkFactory, odFactory, typesFactory, typesManager);
		FileOutputStream out = new FileOutputStream(new File(
				"/Users/Xuan/Desktop/test.json"));
		ProjectJsonExporter.exportProject(project, out);
	}

}
