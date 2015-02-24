package edu.trafficsim.web.service;

import java.util.List;

public class FramesDto {

	long startFrame;

	// vehicle in the format of
	// name,width,length
	List<String> vehicles;

	// element in the format of
	// initFrameId,name,x,y,angle,color
	List<String> elements;
}
