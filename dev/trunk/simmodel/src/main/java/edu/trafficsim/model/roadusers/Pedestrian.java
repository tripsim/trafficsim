package edu.trafficsim.model.roadusers;

import edu.trafficsim.model.core.MovingObject;
import edu.trafficsim.model.network.Path;
import edu.trafficsim.plugin.IPedestrian;

public class Pedestrian extends MovingObject<Pedestrian> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	IPedestrian impl;

	public Pedestrian(double startTime) {
		super(startTime);
	}

	@Override
	protected void before() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void after() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean beyondEnd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Path getPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
