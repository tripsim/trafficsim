package edu.trafficsim.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;

@Controller
@RequestMapping(value = "/node")
public class NodeController extends AbstractController {

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String nodeView(@PathVariable long id, Model model) {
		Network network = project.getNetwork();
		if (network == null)
			return "components/empty";
		Node node = network.getNode(id);
		if (node == null)
			return "components/empty";

		if (network.isDirty())
			network.discover();
		model.addAttribute("network", network);
		model.addAttribute("ods", project.getOdMatrix().getOdsFromNode(node));
		model.addAttribute("node", node);
		return "components/node";
	}
}
