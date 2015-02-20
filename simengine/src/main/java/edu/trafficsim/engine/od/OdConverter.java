package edu.trafficsim.engine.od;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.data.dom.OdDo;
import edu.trafficsim.data.dom.OdMatrixDo;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;

@Service("od-converter")
class OdConverter {

	private static final Logger logger = LoggerFactory
			.getLogger(OdConverter.class);

	@Autowired
	OdFactory factory;

	final void applyOdMatrixDo(OdMatrixDo entity, OdMatrix odMatrix) {
		entity.setMatrixId(odMatrix.getId());
		entity.setName(odMatrix.getName());
		entity.setNetworkName(odMatrix.getNetworkName());
		entity.setOds(toOdDos(odMatrix.getOds()));
	}

	private List<OdDo> toOdDos(Collection<Od> ods) {
		List<OdDo> result = new ArrayList<OdDo>();
		for (Od od : ods) {
			result.add(toOdDo(od));
		}
		return result;
	}

	private OdDo toOdDo(Od od) {
		OdDo result = new OdDo();
		result.setOdId(od.getId());
		result.setName(od.getName());
		result.setOriginNodeId(od.getOriginNodeId());
		result.setDestinationNodeId(od.getDestinationNodeId());
		result.setVehicleTypesComposition(od.getVehicleComposition().getName());
		result.setDriverTypesComposition(od.getDriverComposition().getName());
		result.setTimes(new ArrayList<Double>(od.getJumpTimes()));
		result.setVphs(new ArrayList<Integer>(od.getVphs()));
		return result;
	}

	final OdMatrixDo toOdMatrixDo(OdMatrix odMatrix) {
		OdMatrixDo result = new OdMatrixDo();
		applyOdMatrixDo(result, odMatrix);
		return result;
	}

	final OdMatrix toOdMatrix(OdMatrixDo entity) throws ModelInputException {
		OdMatrix result = factory.createOdMatrix(entity.getMatrixId(),
				entity.getName(), entity.getNetworkName());
		addOdDos(result, entity.getOds());
		return result;
	}

	private void addOdDos(OdMatrix result, List<OdDo> entities)
			throws ModelInputException {
		for (OdDo entity : entities) {
			addOdDo(result, entity);
		}
	}

	private void addOdDo(OdMatrix result, OdDo entity)
			throws ModelInputException {
		if (entity.getTimes().size() != entity.getVphs().size()) {
			logger.warn(
					"inconsistent od encounted in od matrix '{}', od from {} to {} ignored",
					result.getName(), entity.getOriginNodeId(),
					entity.getDestinationNodeId());
		}
		int size = entity.getTimes().size();
		double[] times = new double[size];
		Integer[] vphs = new Integer[size];

		for (int i = 0; i < size; i++) {
			times[i] = entity.getTimes().get(i);
			vphs[i] = entity.getVphs().get(i);
		}
		Od od = factory.createOd(entity.getOdId(), entity.getName(),
				entity.getOriginNodeId(), entity.getDestinationNodeId(),
				entity.getVehicleTypesComposition(),
				entity.getDriverTypesComposition(), times, vphs);
		result.add(od);
	}
}
