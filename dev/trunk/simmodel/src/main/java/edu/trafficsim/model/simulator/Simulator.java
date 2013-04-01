package edu.trafficsim.model.simulator;


public class Simulator extends AbstractSimulator<Simulator> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final long DEFAULT_SEED = 0l;

	public Simulator(double duration, double stepSize) {
		this(duration, stepSize, DEFAULT_SEED);
	}

	public Simulator(double duration, double stepSize, long seed) {
		super(duration, stepSize, seed);
	}
}
