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

import edu.trafficsim.api.model.Network;
import edu.trafficsim.api.model.Node;
import edu.trafficsim.api.model.Od;
import edu.trafficsim.api.model.OdMatrix;
import edu.trafficsim.engine.od.OdFactory;
import edu.trafficsim.engine.type.TypesManager;
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

	private static final String DEFAULT_NAME = "odmatrix";

	@Autowired
	TypesManager typesManager;

	@Autowired
	OdFactory factory;

	public void updateOd(OdMatrix odMatrix, Network network, Long id, Long dId,
			String vcName, String dcName, double[] times, Integer[] vphs) {
		Od od = odMatrix.getOd(id);
		if (od == null) {
			throw new IllegalArgumentException("od '" + id + "' doesn't exist!");
		}
		od.setDestination(network.containsNode(dId) ? dId : null);
		od.setVehicleComposition(typesManager.getVehicleTypeComposition(vcName));
		od.setDriverComposition(typesManager.getDriverTypeComposition(dcName));
		od.setVphs(times, vphs);
		odMatrix.setModified(true);
	}

	public Od createOd(Sequence sequence, OdMatrix odMatrix, Node origin,
			Node destination) {
		Od od = factory.createOd(sequence.nextId(), origin.getId(),
				destination == null ? null : destination.getId(),
				typesManager.getDefaultVehicleTypeCompositionName(),
				typesManager.getDefaultDriverTypeCompositionName(),
				DEFAULT_TIME_POINTS, DEFAULT_TIME_VPH);
		odMatrix.add(od);
		odMatrix.setModified(true);
		return od;
	}

	public void removeOd(OdMatrix odMatrix, long id) {
		odMatrix.remove(id);
		odMatrix.setModified(true);
	}

	public OdMatrix createOdMatrix(Sequence sequence, String networkName) {
		OdMatrix odMatrix = factory.createOdMatrix(DEFAULT_NAME, networkName);
		odMatrix.setModified(true);
		return odMatrix;
	}

}
