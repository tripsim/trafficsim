package edu.trafficsim.web.service;

import java.util.List;

public class TrajectoriesDto {

	long nodeId;

	long startFrame;

	// trajectory in the format of
	// linear geom
	List<String> trajectories;
}
