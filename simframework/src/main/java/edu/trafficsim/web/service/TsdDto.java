package edu.trafficsim.web.service;

import java.util.List;

public class TsdDto {

	long linkId;

	long startFrame;

	// series in the format of
	// a list of points [x1,y1],[x2,y2],...
	List<String> serieses;
}
