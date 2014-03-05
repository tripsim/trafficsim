package edu.trafficsim.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.OdFactory;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.utility.Sequence;
import edu.trafficsim.web.service.entity.NetworkService;
import edu.trafficsim.web.service.entity.OdService;

@Controller
@RequestMapping(value = "/scenario")
@SessionAttributes(value = { "sequence", "networkFactory", "odFactory",
		"network", "odMatrix" })
public class ScenarioController extends AbstractController {

	@Autowired
	NetworkService networkService;
	@Autowired
	OdService odService;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newScenarioView(@ModelAttribute("sequence") Sequence seq,
			@ModelAttribute("networkFactory") NetworkFactory networkFactory,
			@ModelAttribute("odFactory") OdFactory odFactory, Model model) {

		Network network = networkService.createNetwork(networkFactory, seq);
		OdMatrix odMatrix = odService.createOdMatrix(odFactory, seq);
		model.addAttribute("network", network);
		model.addAttribute("odMatrix", odMatrix);
		return "components/scenario-new";
	}

}
