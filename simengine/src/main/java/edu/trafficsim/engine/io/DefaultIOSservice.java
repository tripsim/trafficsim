package edu.trafficsim.engine.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.io.ParseException;

import edu.trafficsim.engine.network.NetworkFactory;
import edu.trafficsim.engine.od.OdFactory;
import edu.trafficsim.engine.type.TypesFactory;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.model.core.ModelInputException;

@Service("default-io-service")
class DefaultIOSservice implements IOService {

	private final static Logger log = LoggerFactory
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
			log.error("failed to export from source!");
		}

	}

	@Override
	public SimulationProject importProject(InputStream in) {
		try {
			return ProjectJsonImporter.importProject(in, networkFactory,
					odFactory, typesFactory, typesManager);
		} catch (IOException | ParseException | ModelInputException
				| TransformException e) {
			log.error("failed to import from source!");
		}
		return null;
	}

}