package edu.trafficsim.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/scenario")
public class ScenarioController extends AbstractController {

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newScenarioView() {
		project.clear();
		return "components/scenario-new";
	}
}
