package edu.trafficsim.model.network;

public class Route extends AbstractNetwork<Route> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Node start;
	private Node end;

	public Route(Node start, Node end) {
		this.start = start;
		this.end = end;
	}

	public Node getStartNode() {
		return start;
	}

	public Node getEndNode() {
		return end;
	}

}
