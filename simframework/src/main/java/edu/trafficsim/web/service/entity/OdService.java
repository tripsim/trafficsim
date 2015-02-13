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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.engine.od.OdFactory;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.Sequence;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Service("od-service")
public class OdService extends EntityService {

	// in seconds
	private static final double[] DEFAULT_TIME_POINTS = new double[] { 100 };
	// in vph
	private static final Integer[] DEFAULT_TIME_VPH = new Integer[] { 1000 };

	private static final String DEFAULT_NAME = "";

	@Autowired
	TypesManager typesManager;

	@Autowired
	OdFactory factory;

	public void updateOd(Network network, OdMatrix odMatrix, Long id, Long dId,
			String vcName, String dcName, double[] times, Integer[] vphs)
			throws ModelInputException {
		odMatrix.getOd(id).setDestination(network.getNode(dId));
		odMatrix.getOd(id).setVehicleComposition(
				typesManager.getVehicleTypeComposition(vcName));
		odMatrix.getOd(id).setDriverComposition(
				typesManager.getDriverTypeComposition(dcName));
		odMatrix.getOd(id).setVphs(times, vphs);
	}

	public Od createOd(Sequence sequence, OdMatrix odMatrix, Node origin,
			Node destination) throws ModelInputException {
		Od od = factory.createOd(sequence.nextId(), DEFAULT_NAME, origin,
				destination, typesManager.getDefaultVehicleTypeComposition(),
				typesManager.getDefaultDriverTypeComposition(),
				DEFAULT_TIME_POINTS, DEFAULT_TIME_VPH);
		odMatrix.add(od);
		return od;
	}

	public void removeOd(OdMatrix odMatrix, long id) {
		odMatrix.remove(id);
	}

	public OdMatrix createOdMatrix(Sequence sequence) {
		return factory.createOdMatrix(sequence.nextId());
	}

}
