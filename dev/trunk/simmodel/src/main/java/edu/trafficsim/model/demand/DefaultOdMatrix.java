package edu.trafficsim.model.demand;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.trafficsim.model.BaseEntity;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.MultiKey;
import edu.trafficsim.model.core.MultiValuedMap;

public class DefaultOdMatrix extends BaseEntity<DefaultOdMatrix> implements
		OdMatrix {

	private static final long serialVersionUID = 1L;

	// origin, destination pair -> od
	public final MultiValuedMap<OdKey, Od> ods;
	public final Map<Long, Od> odsById;

	public DefaultOdMatrix(long id, String name) {
		super(id, name);
		ods = new MultiValuedMap<OdKey, Od>();
		odsById = new HashMap<Long, Od>();
	}

	@Override
	public Collection<Od> getOdsFromNode(Node node) {
		Set<Od> newOds = new HashSet<Od>();
		for (OdKey odKey : ods.keys()) {
			if (odKey.primaryKey() == node)
				newOds.addAll(ods.get(odKey));
		}
		return Collections.unmodifiableCollection(newOds);
	}

	@Override
	public Collection<Od> getOds() {
		return odsById.values();
	}

	@Override
	public Od getOd(long id) {
		return odsById.get(id);
	}

	@Override
	public void add(Od od) {
		ods.add(key(od), od);
		odsById.put(od.getId(), od);
	}

	@Override
	public void remove(long id) {
		Od od = odsById.remove(id);
		if (od != null)
			ods.remove(key(od), od);
	}

	private static final OdKey key(Od od) {
		return new OdKey(od.getOrigin(), od.getDestination());
	}

	private static final class OdKey extends MultiKey<Node, Node> {
		private static final long serialVersionUID = 1L;

		public OdKey(Node key1, Node key2) {
			super(key1, key2);
		}

	}

}
