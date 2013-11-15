package edu.trafficsim.plugin;

import org.opengis.referencing.operation.TransformException;

import edu.trafficsim.engine.StatisticsCollector;

public interface ISimulation {

	public void run() throws TransformException;

	public StatisticsCollector statistics();
}
