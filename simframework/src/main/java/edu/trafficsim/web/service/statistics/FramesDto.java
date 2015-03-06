package edu.trafficsim.web.service.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FramesDto implements Serializable {

	private static final long serialVersionUID = 1L;

	long startFrame;

	// vehicle in the format of
	// name,width,length
	List<String> vehicles = new ArrayList<String>();

	// element in the format of
	// frameId,name,x,y,angle,color
	List<String> elements = new ArrayList<String>();

	FramesDto(long startFrame) {
		this.startFrame = startFrame;
	}

	public long getStartFrame() {
		return startFrame;
	}

	public List<String> getVehicles() {
		return vehicles;
	}

	void setVehicles(List<String> vehicles) {
		this.vehicles = vehicles;
	}

	public List<String> getElements() {
		return elements;
	}

	void setElements(List<String> elements) {
		this.elements = elements;
	}

}