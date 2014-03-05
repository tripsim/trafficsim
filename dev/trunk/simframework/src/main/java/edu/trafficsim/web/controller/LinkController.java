package edu.trafficsim.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.utility.Sequence;
import edu.trafficsim.web.UserInterfaceException;
import edu.trafficsim.web.service.MapJsonService;
import edu.trafficsim.web.service.entity.NetworkService;
import edu.trafficsim.web.service.entity.OsmImportService.OsmHighwayValue;

@Controller
@RequestMapping(value = "/link")
@SessionAttributes(value = { "sequence", "networkFactory", "network",
		"odMatrix" })
public class LinkController extends AbstractController {

	@Autowired
	NetworkService networkService;
	@Autowired
	MapJsonService mapJsonService;

	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public String linkView(@PathVariable long id,
			@ModelAttribute("network") Network network, Model model) {
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
	public String linkInfo(@PathVariable long id,
			@ModelAttribute("network") Network network, Model model) {
		Link link = network.getLink(id);
		if (link == null)
			return "components/empty";

		model.addAttribute("link", link);
		return "components/link :: info";
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public String linkEdit(@PathVariable long id,
			@ModelAttribute("network") Network network, Model model) {
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
			@RequestParam("roadName") String roadName,
			@ModelAttribute("network") Network network) {
		Link link = network.getLink(id);
		networkService.saveLink(link, name, highway, roadName);
		return messageOnlySuccessResponse("Link saved.");
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> removeLink(@RequestParam("id") long id,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix) {
		try {
			Link reverse = network.getLink(id).getReverseLink();
			Map<String, Object> data = new HashMap<String, Object>();
			for (Map.Entry<String, Set<String>> entry : networkService
					.removeLink(network, odMatrix, id).entrySet()) {
				data.put(entry.getKey(), entry.getValue());
			}
			if (reverse != null) {
				data.put(
						"lanesconnectors",
						mapJsonService.getLanesConnectorsJson(network,
								reverse.getId()));
			} else {
				data.put("lanesconnectors",
						mapJsonService.getEmptyLanesConnectorsJson());
			}
			return successResponse("Link removed.", null, data);
		} catch (TransformException e) {
			return failureResponse("Transformation issues!");
		}
	}

	@RequestMapping(value = "/createreverse", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> createReverseLink(@RequestParam("id") long id,
			@ModelAttribute("networkFactory") NetworkFactory factory,
			@ModelAttribute("sequence") Sequence seq,
			@ModelAttribute("network") Network network) {
		try {
			Link reverse = networkService.createReverseLink(factory, seq,
					network, id);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("link",
					mapJsonService.getLinkJson(network, reverse.getId()));
			data.put("lanesconnectors",
					mapJsonService.getLanesConnectorsJson(network, id));
			return successResponse("Link removed.", "link/view/" + id, data);
		} catch (TransformException e) {
			return failureResponse("Transformation issues!");
		} catch (UserInterfaceException e) {
			return failureResponse(e);
		} catch (ModelInputException e) {
			return failureResponse(e);
		}
	}
}
