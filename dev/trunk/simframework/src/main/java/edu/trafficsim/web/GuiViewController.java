package edu.trafficsim.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.web.service.ExtractOsmNetworkService.OsmHighwayValue;

@Controller
@RequestMapping(value = "/view")
public class GuiViewController {

	@Autowired
	SimulationProject project;

	@RequestMapping(value = "/scenario-new", method = RequestMethod.GET)
	public String newScenarioView() {
		project.reset();
		return "components/scenario-new";
	}

	@RequestMapping(value = "/network-new", method = RequestMethod.GET)
	public String newNetworkView(Model model) {
		model.addAttribute("options", OsmHighwayValue.values());
		return "components/network-new";
	}

	@RequestMapping(value = "/network", method = RequestMethod.GET)
	public String networkView(Model model) {
		Network network = project.getNetwork();
		if (network == null)
			return "components/empty";
		model.addAttribute("network", network);
		model.addAttribute("linkCount", network.getLinks().size());
		model.addAttribute("nodeCount", network.getNodes().size());
		model.addAttribute("sourceCount", network.getSources().size());
		model.addAttribute("sinkCount", network.getSinks().size());
		return "components/network";
	}

	@RequestMapping(value = "/link/{id}", method = RequestMethod.GET)
	public String linkView(@PathVariable long id, Model model) {
		Network network = project.getNetwork();
		if (network == null)
			return "components/empty";
		Link link = network.getLink(id);
		if (link == null)
			return "components/empty";
		List<Link> links = new ArrayList<Link>(2);
		links.add(link);
		if (link.getReverseLink() != null)
			links.add(link.getReverseLink());

		model.addAttribute("links", links);
		return "components/link";
	}

	@RequestMapping(value = "/simulator", method = RequestMethod.GET)
	public String simulatorViw() {
		return "components/simulator";
	}
}
