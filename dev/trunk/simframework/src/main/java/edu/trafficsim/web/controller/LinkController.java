package edu.trafficsim.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;

@Controller
@RequestMapping(value = "/link")
public class LinkController extends AbstractController {

	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
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

	// TODO currently not calling from anybody
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String linkEdit(@PathVariable long id, Model model) {
		Network network = project.getNetwork();
		if (network == null)
			return "components/empty";
		Link link = network.getLink(id);
		if (link == null)
			return "components/empty";

		model.addAttribute("link", link);
		return "components/link-edit";
	}

}
