package edu.trafficsim.web.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.trafficsim.model.SimulationScenario;
import edu.trafficsim.web.SimulationProject;

@Service
public class ScenarioService extends EntityService {
	@Autowired
	SimulationProject project;

	public SimulationScenario createScenario() {
		return project.getScenarioFactory().createSimulationScenario(
				project.nextSeq(), "", project.getSimulator(),
				project.getNetwork(), project.getOdMatrix());
	}
}
