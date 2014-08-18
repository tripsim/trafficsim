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

/**
 * 
 * 
 * @author Xuan Shi
 */
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
