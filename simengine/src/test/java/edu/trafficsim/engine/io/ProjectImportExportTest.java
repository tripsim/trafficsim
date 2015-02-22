package edu.trafficsim.engine.io;

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

import com.vividsolutions.jts.io.ParseException;

import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.engine.od.OdFactory;
import edu.trafficsim.engine.simulation.SimulationProject;
import edu.trafficsim.engine.type.TypesFactory;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.model.core.ModelInputException;

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
	public void testExport() throws IOException, ParseException,
			ModelInputException {
		FileInputStream in = new FileInputStream(new File(
				"/Users/Xuan/Desktop/test.json"));
		SimulationProject project = ProjectJsonImporter.importProject(in,
				networkFactory, odFactory, typesFactory, typesManager);
		FileOutputStream out = new FileOutputStream(new File(
				"/Users/Xuan/Desktop/test.json"));
		ProjectJsonExporter.exportProject(project, out);
	}

}
