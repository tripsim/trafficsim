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
