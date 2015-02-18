package edu.trafficsim.engine.od;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.data.dom.OdDo;
import edu.trafficsim.data.dom.OdMatrixDo;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;

@Service("od-converter")
class OdConverter {

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
		result.setVphs(toVphsMap(od.getJumpTimes(), od.getVphs()));
		return result;
	}

	private Map<Double, Integer> toVphsMap(Collection<Double> times,
			Collection<Integer> vphs) {
		Map<Double, Integer> result = new HashMap<Double, Integer>();
		Iterator<Double> it1 = times.iterator();
		Iterator<Integer> it2 = vphs.iterator();
		while (it1.hasNext() && it2.hasNext()) {
			result.put(it1.next(), it2.next());
		}
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
		double[] times = new double[entity.getVphs().size()];
		Integer[] vphs = new Integer[entity.getVphs().size()];
		int i = 0;
		for (Map.Entry<Double, Integer> entry : entity.getVphs().entrySet()) {
			times[i] = entry.getKey();
			vphs[i] = entry.getValue();
			i++;
		}
		Od od = factory.createOd(entity.getOdId(), entity.getName(),
				entity.getOriginNodeId(), entity.getDestinationNodeId(),
				entity.getVehicleTypesComposition(),
				entity.getDriverTypesComposition(), times, vphs);
		result.add(od);
	}
}
