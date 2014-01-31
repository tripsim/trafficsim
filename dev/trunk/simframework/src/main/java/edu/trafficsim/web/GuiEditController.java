package edu.trafficsim.web;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

}
