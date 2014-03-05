package edu.trafficsim.web.service.entity;

import org.springframework.stereotype.Service;

import edu.trafficsim.engine.OdFactory;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.utility.Sequence;
import edu.trafficsim.web.UserInterfaceException;

@Service
public class OdService extends EntityService {

	public void updateOd(TypesLibrary library, Network network,
			OdMatrix odMatrix, Long id, Long dId, String vcName,
			double[] times, Integer[] vphs) throws ModelInputException {
		odMatrix.getOd(id).setDestination(network.getNode(dId));
		odMatrix.getOd(id).setVehicleComposiion(
				library.getVehicleComposition(vcName));
		odMatrix.getOd(id).setVphs(times, vphs);
	}

	public Od createOd(TypesLibrary library, OdFactory factory, Sequence seq,
			OdMatrix odMatrix, Node origin, Node destination)
			throws ModelInputException, UserInterfaceException {
		Od od = factory.createOd(seq, "", origin, destination,
				library.getDefaultVehicleComposition(),
				library.getDefaultDriverComposition());
		odMatrix.add(od);
		return od;
	}

	public void removeOd(OdMatrix odMatrix, long id) {
		odMatrix.remove(id);
	}

	public OdMatrix createOdMatrix(OdFactory factory, Sequence seq) {
		return factory.createOdMatrix(seq);
	}

}
