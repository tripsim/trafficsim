package edu.trafficsim.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.trafficsim.model.Simulator;

@Controller
@RequestMapping(value = "/simulator")
public class SimulatorController extends AbstractController {

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String simulatorViw(Model model) {
		Simulator simulator = project.getSimulator();
		if (simulator == null)
			return "components/empty";

		model.addAttribute("simulator", simulator);
		return "components/simulator";
	}
}
