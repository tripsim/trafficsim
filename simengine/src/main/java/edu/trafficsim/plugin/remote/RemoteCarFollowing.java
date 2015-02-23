package edu.trafficsim.plugin.remote;

import org.springframework.stereotype.Component;

import edu.trafficsim.engine.simulation.Tracker;
import edu.trafficsim.model.Vehicle;
import edu.trafficsim.plugin.AbstractRemotePlugin;
import edu.trafficsim.plugin.ICarFollowing;

@Component("Remote Car-following")
public class RemoteCarFollowing extends AbstractRemotePlugin implements
		ICarFollowing {

	private static final long serialVersionUID = 1L;

	@Override
	public void update(Vehicle vehicle, Tracker tracker) {
		
	}



}
