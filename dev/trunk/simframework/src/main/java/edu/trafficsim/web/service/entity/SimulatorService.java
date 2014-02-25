package edu.trafficsim.web.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.model.Simulator;
import edu.trafficsim.web.SimulationProject;

@Service
public class SimulatorService extends EntityService {
	
	@Autowired
	SimulationProject project;

	public Simulator createSimulator() {
		return project.getScenarioFactory().createSimulator(project.nextSeq());
	}
}
