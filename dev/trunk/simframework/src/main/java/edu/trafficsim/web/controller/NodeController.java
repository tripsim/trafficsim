package edu.trafficsim.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.OdMatrix;

@Controller
@RequestMapping(value = "/node")
@SessionAttributes(value = { "network", "odMatrix" })
public class NodeController extends AbstractController {

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String nodeView(@PathVariable long id,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix, Model model) {
		if (network == null)
			return "components/empty";
		Node node = network.getNode(id);
		if (node == null)
			return "components/empty";

		if (network.isDirty())
			network.discover();
		model.addAttribute("network", network);
		model.addAttribute("ods", odMatrix.getOdsFromNode(node));
		model.addAttribute("node", node);
		return "components/node";
	}
}
