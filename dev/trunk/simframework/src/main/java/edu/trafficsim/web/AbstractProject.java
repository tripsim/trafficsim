package edu.trafficsim.web;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.OdFactory;
import edu.trafficsim.engine.StatisticsCollector;
import edu.trafficsim.engine.TypesFactory;
import edu.trafficsim.engine.factory.DefaultNetworkFactory;
import edu.trafficsim.engine.factory.DefaultOdFactory;
import edu.trafficsim.engine.factory.DefaultTypesFactory;
import edu.trafficsim.engine.statistics.DefaultStatisticsCollector;
import edu.trafficsim.utility.Timer;

public abstract class AbstractProject {

	// TODO set factory through settings
	protected NetworkFactory networkFactory;
	protected OdFactory odFactory;
	protected TypesFactory typesFactory;

	Timer timer;
	StatisticsCollector statistics;

	public AbstractProject() {
		networkFactory = DefaultNetworkFactory.getInstance();
		odFactory = DefaultOdFactory.getInstance();
		typesFactory = DefaultTypesFactory.getInstance();
		statistics = DefaultStatisticsCollector.create();
		timer = Timer.create();
	}

	public NetworkFactory getNetworkFactory() {
		return networkFactory;
	}

	public void setNetworkFactory(NetworkFactory factory) {
		networkFactory = factory;
	}

	public OdFactory getOdFactory() {
		return odFactory;
	}

	public void setOdFactory(OdFactory factory) {
		odFactory = factory;
	}

	public TypesFactory getTypesFactory() {
		return typesFactory;
	}

	public void setTypesFactory(TypesFactory factory) {
		typesFactory = factory;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public StatisticsCollector getStatistics() {
		return statistics;
	}

}
