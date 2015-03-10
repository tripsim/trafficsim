package org.tripsim.web.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.tripsim.api.model.Od;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OdCandidates implements Serializable {
	private static final long serialVersionUID = 1L;

	Map<Long, Od> candidates = new HashMap<Long, Od>();

	public void add(Od od) {
		if (od == null) {
			return;
		}
		candidates.put(od.getId(), od);
	}

	public Od get(long id) {
		return candidates.get(id);
	}

	public Od remove(long id) {
		return candidates.remove(id);
	}
}
