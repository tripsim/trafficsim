package edu.trafficsim.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/simulator")
public class SimulatorController extends AbstractController {

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String simulatorViw() {
		return "components/simulator";
	}
}
