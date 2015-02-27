package edu.trafficsim.web.service;

import java.io.Serializable;
import java.util.List;

public class TsdDto implements Serializable {

	private static final long serialVersionUID = 1L;

	long linkId;

	long startFrame;

	// series in the format of
	// a list of points [time1,position1],[time2,position2],...
	List<List<String>> serieses;

	TsdDto(long linkId, long startFrame) {
		this.linkId = linkId;
		this.startFrame = startFrame;
	}

	public List<List<String>> getSerieses() {
		return serieses;
	}

	void setSerieses(List<List<String>> serieses) {
		this.serieses = serieses;
	}

}
