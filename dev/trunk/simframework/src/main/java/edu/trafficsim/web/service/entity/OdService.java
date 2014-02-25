package edu.trafficsim.web.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.SimulationProject;
import edu.trafficsim.web.UserInterfaceException;

@Service
public class OdService extends EntityService {

	@Autowired
	SimulationProject project;
	@Autowired
	CompositionService compositionService;

	public void updateOd(Long id, Long did, double[] times, Integer[] vphs)
			throws ModelInputException {
		project.getOdMatrix().getOd(id)
				.setDestination(project.getNetwork().getNode(did));
		project.getOdMatrix().getOd(id).setVphs(times, vphs);
	}

	public Od createOd(Node origin, Node destination)
			throws ModelInputException, UserInterfaceException {
		Od od = project.getScenarioFactory().createOd(project.nextSeq(), "",
				origin, destination, project.getDefaultVehicleComposition(),
				project.getDefaultDriverComposition());
		project.getOdMatrix().add(od);
		return od;
	}

	public void removeOd(long id) {
		project.getOdMatrix().remove(id);
	}

	public OdMatrix createOdMatrix() {
		return project.getScenarioFactory().createOdMatrix(project.nextSeq());
	}

}
