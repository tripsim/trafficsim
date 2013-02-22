package edu.trafficsim.model.simulator;

import java.util.Date;

import edu.trafficsim.model.core.BaseEntity;

public abstract class AbstractSimulator<T> extends BaseEntity<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date startTime;
	private double warmup; // in seconds
	private double duration; // in seconds
	private double stepSize; // in seconds
	
	private double clock; // indicates the current time in seconds
	

}
