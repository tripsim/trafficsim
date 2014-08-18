/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.web.service.entity;

import org.springframework.stereotype.Service;

import edu.trafficsim.engine.OdFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.UserInterfaceException;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service
public class OdService extends EntityService {

	public void updateOd(TypesLibrary library, Network network,
			OdMatrix odMatrix, Long id, Long dId, String vcName, String dcName,
			double[] times, Integer[] vphs) throws ModelInputException {
		odMatrix.getOd(id).setDestination(network.getNode(dId));
		odMatrix.getOd(id).setVehicleComposition(
				library.getVehicleComposition(vcName));
		odMatrix.getOd(id).setDriverComposition(
				library.getDriverComposition(dcName));
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
