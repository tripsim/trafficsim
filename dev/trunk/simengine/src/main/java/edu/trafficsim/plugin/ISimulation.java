package edu.trafficsim.plugin;

import edu.trafficsim.engine.StatisticsCollector;

public interface ISimulation {

	public void run();

	public StatisticsCollector statistics();
}
