package edu.trafficsim.web.controller;

import java.util.Map;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.utility.Timer;
import edu.trafficsim.web.SimulationProject;
import edu.trafficsim.web.service.SimulationService;

@Controller
@RequestMapping(value = "/simulator")
@SessionAttributes(value = { "sequence", "network", "odMatrix" })
public class SimulatorController extends AbstractController {

	@Autowired
	SimulationProject project;
	@Autowired
	SimulationService simulationService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String simulatorViw(Model model) {
		Timer timer = project.getTimer();
		if (timer == null)
			project.setTimer(timer = simulationService.createTimer());

		model.addAttribute("timer", timer);
		return "components/simulator";
	}

	@RequestMapping(value = "/run", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> runSimulation(@RequestParam("duration") int duration,
			@RequestParam("warmup") int warmup,
			@RequestParam("stepSize") double stepSize,
			@RequestParam("seed") long seed,
			@ModelAttribute("sequence") Sequence seq,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix) {
		simulationService.setTimer(project, stepSize, duration, warmup, seed);

		try {
			simulationService.runSimulation(project, seq, network, odMatrix);
		} catch (TransformException e) {
			return failureResponse("Transform issues!");
		}
		return messageOnlySuccessResponse("Simulation finished.");
	}

}
