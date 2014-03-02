package edu.trafficsim.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.web.service.entity.NetworkService;
import edu.trafficsim.web.service.entity.OsmImportService.OsmHighwayValue;

@Controller
@RequestMapping(value = "/link")
public class LinkController extends AbstractController {

	@Autowired
	NetworkService networkService;

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

	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	public String linkInfo(@PathVariable long id, Model model) {
		Network network = project.getNetwork();
		if (network == null)
			return "components/empty";
		Link link = network.getLink(id);
		if (link == null)
			return "components/empty";

		model.addAttribute("link", link);
		return "components/link :: info";
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public String linkEdit(@PathVariable long id, Model model) {
		Network network = project.getNetwork();
		if (network == null)
			return "components/empty";
		Link link = network.getLink(id);
		if (link == null)
			return "components/empty";

		model.addAttribute("highways", OsmHighwayValue.values());
		model.addAttribute("link", link);
		return "components/link-fragments :: form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> saveLink(@RequestParam("id") long id,
			@RequestParam("name") String name,
			@RequestParam("highway") String highway,
			@RequestParam("roadName") String roadName) {
		Network network = project.getNetwork();
		Link link = network.getLink(id);

		networkService.saveLink(link, name, highway, roadName);
		return messageOnlySuccessResponse("Link saved.");
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> removeLink(@RequestParam("id") long id) {
		return successResponse("Link removed.", networkService.removeLink(id));
	}
}
