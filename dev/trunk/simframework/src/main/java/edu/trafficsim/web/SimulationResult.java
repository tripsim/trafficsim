package edu.trafficsim.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.engine.statistics.DefaultStatisticsCollector;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SimulationResult {

	@Autowired
	SimulationProject project;

	StatisticsCollector statistics = null;

	public void clear() {
		statistics = null;
	}

	public boolean isEmpty() {
		return statistics == null;
	}

	public StatisticsCollector getStatistics() {
		if (statistics == null)
			this.statistics = DefaultStatisticsCollector.create(project
					.getSimulator());
		return statistics;
	}

	public Collection<Long> getVehicleIds() {
		return getStatistics().getVehicleIds();
	}

	public Collection<Long> getLinkIds() {
		return getStatistics().getLinkIds();
	}

}
