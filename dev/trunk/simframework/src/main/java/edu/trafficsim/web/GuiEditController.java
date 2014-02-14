package edu.trafficsim.web;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.service.NetworkEditService;

@Controller
@RequestMapping(value = "/edit")
public class GuiEditController {

	@Autowired
	SimulationProject project;
	@Autowired
	NetworkEditService networkEdit;

	@RequestMapping(value = "/link/{id}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/addlanetolink/{id}", method = RequestMethod.GET)
	public String addLane(@PathVariable long id, Model model) {
		Network network = project.getNetwork();
		if (network == null)
			return "components/empty";
		Link link = network.getLink(id);
		if (link == null)
			return "components/empty";

		try {
			networkEdit.addLane(link, project.getNetworkFactory());
		} catch (ModelInputException e) {
			e.printStackTrace();
		} catch (TransformException e) {
			e.printStackTrace();
		}

		model.addAttribute("link", link);
		return "components/link-edit";
	}

	@RequestMapping(value = "/removelane/{laneId}/fromlink/{id}", method = RequestMethod.GET)
	public String removeLane(@PathVariable int laneId, @PathVariable long id,
			Model model) {
		Network network = project.getNetwork();
		if (network == null)
			return "components/empty";
		Link link = network.getLink(id);
		if (link == null)
			return "components/empty";

		try {
			networkEdit.removeLane(link, laneId);
		} catch (ModelInputException e) {
			e.printStackTrace();
		} catch (TransformException e) {
			e.printStackTrace();
		}

		model.addAttribute("link", link);
		return "components/link-edit";
	}

	@RequestMapping(value = "/connectlanes", method = RequestMethod.POST)
	public @ResponseBody
	String connectLanes(@RequestParam("link1") long link1Id,
			@RequestParam("lane1") int lane1Id,
			@RequestParam("link2") long link2Id,
			@RequestParam("lane2") int lane2Id) {
		Network network = project.getNetwork();
		if (network == null)
			return "";
		Link link1 = network.getLink(link1Id);
		if (link1 == null)
			return "";
		Lane lane1 = link1.getLane(lane1Id);
		if (lane1 == null)
			return "";
		Link link2 = network.getLink(link2Id);
		if (link2 == null)
			return "";
		Lane lane2 = link2.getLane(lane2Id);
		if (lane2 == null)
			return "";

		try {
			if (link1.getEndNode() == link2.getStartNode()) {
				if (link1.getEndNode().isConnected(lane1, lane2)) {
					return "already connected";
				}
				networkEdit.connectLanes(lane1, lane2,
						project.getNetworkFactory());
				return "success 1";
			} else if (link1.getStartNode() == link2.getEndNode()) {
				if (link1.getStartNode().isConnected(lane2, lane1)) {
					return "already connected";
				}
				networkEdit.connectLanes(lane2, lane1,
						project.getNetworkFactory());
				return "success 2";
			} else {
				return "";
			}
		} catch (ModelInputException | TransformException e) {
			e.printStackTrace();
		}

		return "null";
	}

}
