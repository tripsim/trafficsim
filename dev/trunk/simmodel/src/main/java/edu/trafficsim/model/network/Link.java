package edu.trafficsim.model.network;

import java.util.ArrayList;
import java.util.List;

import edu.trafficsim.model.core.AbstractSegment;
import edu.trafficsim.model.roadusers.VehicleQueues;

public class Link extends AbstractSegment<Link> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LinkType type;
	private List<Lane> lanes;
	private VehicleQueues queues;
	
	public Link(LinkType type, String name, Node fromNode, Node toNode) {
		super(fromNode, toNode);
		lanes = new ArrayList<Lane>();
		setName(name);
		this.type = type;
		this.queues = new VehicleQueues();
	}
	
	
	public Node getFromNode() {
		return (Node) getFromLocation();
	}
	
	
	public Node getToNode() {
		return (Node) getToLocation();
	}
	
	public List<Lane> getLanes() {
		return lanes;
	}


	public LinkType getType() {
		return type;
	}


	public void setType(LinkType type) {
		this.type = type;
	}
	
	public VehicleQueues getVehicleQueues() {
		return queues;
	}

}
