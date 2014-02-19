package edu.trafficsim.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.trafficsim.model.ConnectionLane;
import edu.trafficsim.model.Lane;
import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.web.service.ExtractOsmNetworkService.OsmHighwayValue;

@Controller
@RequestMapping(value = "/view")
public class GuiViewController {

	@Autowired
	SimulationProject project;
	@Autowired
	VehicleTypeRepo vehicleRepo;

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

	@RequestMapping(value = "/connector/{fromLinkId}/{toLinkId}", method = RequestMethod.GET)
	public String connectorView(
			@PathVariable long fromLinkId,
			@MatrixVariable(value = "laneId", pathVar = "fromLinkId") int fromLaneId,
			@PathVariable long toLinkId,
			@MatrixVariable(value = "laneId", pathVar = "toLinkId") int toLaneId,
			Model model) {
		Network network = project.getNetwork();
		if (network == null)
			return "components/empty";
		Link fromLink = network.getLink(fromLinkId);
		if (fromLink == null)
			return "components/empty";
		Lane fromLane = fromLink.getLane(fromLaneId);
		if (fromLane == null)
			return "components/empty";
		Link toLink = network.getLink(toLinkId);
		if (toLink == null)
			return "components/empty";
		Lane toLane = toLink.getLane(toLaneId);
		if (toLane == null)
			return "components/empty";

		ConnectionLane connector = fromLane.getLink().getEndNode()
				.getConnector(fromLane, toLane);
		if (connector == null)
			return "components/empty";

		model.addAttribute("connector", connector);
		return "components/connector";
	}

	@RequestMapping(value = "/node/{id}", method = RequestMethod.GET)
	public String nodeView(@PathVariable long id, Model model) {
		Network network = project.getNetwork();
		if (network == null)
			return "components/empty";
		Node node = network.getNode(id);
		if (node == null)
			return "components/empty";

		model.addAttribute("network", network);
		model.addAttribute("ods", project.getOdMatrix().getOdsFromNode(node));
		model.addAttribute("node", node);
		return "components/node";
	}

	@RequestMapping(value = "/vehiclecomposition", method = RequestMethod.GET)
	public String viechleCompositionView(Model model) {
		model.addAttribute("vehicleCompositions",
				vehicleRepo.getVehicleTypeCompositions());
		return "components/vehiclecomposition";
	}

	@RequestMapping(value = "/simulator", method = RequestMethod.GET)
	public String simulatorViw() {
		return "components/simulator";
	}
}
