package edu.trafficsim.model.demand;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.trafficsim.model.core.BaseEntity;

public class DemandInterval extends BaseEntity<DemandInterval> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// intervals represents the timeline by continuing interval (in seconds)
	private List<Double> intervals;
	
	public DemandInterval() {
		intervals = new ArrayList<Double>();
	}

	public Queue<Double> getIntervals() {
		return new LinkedList<Double>(intervals);
	}
	
}
