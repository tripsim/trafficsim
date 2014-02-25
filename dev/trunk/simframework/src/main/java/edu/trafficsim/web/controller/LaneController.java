package edu.trafficsim.web.controller;

import java.util.Map;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.service.MapJsonService;
import edu.trafficsim.web.service.entity.NetworkService;

@Controller
@RequestMapping(value = "/lane")
public class LaneController extends AbstractController {

	@Autowired
	NetworkService networkService;
	@Autowired
	MapJsonService mapJsonService;

	@RequestMapping(value = "/addtolink", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> addLane(@RequestParam("id") long id) {
		Network network = project.getNetwork();
		if (network == null) {
			return failureResponse("no network.");
		}
		Link link = network.getLink(id);
		if (link == null) {
			return failureResponse("link doesn't exist.");
		}

		try {
			networkService.addLane(link, project.getNetworkFactory());
		} catch (ModelInputException e) {
			e.printStackTrace();
		} catch (TransformException e) {
			e.printStackTrace();
		}

		return laneUpdatedResponse(network, id);
	}

	@RequestMapping(value = "/removefromlink", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> removeLane(@RequestParam("laneId") int laneId,
			@RequestParam("linkId") long linkId) {

		Network network = project.getNetwork();
		if (network == null) {
			return failureResponse("no network.");
		}
		Link link = network.getLink(linkId);
		if (link == null) {
			return failureResponse("link doesn't exist.");
		}

		try {
			networkService.removeLane(link, laneId);
		} catch (TransformException e) {
			e.printStackTrace();
		}

		return laneUpdatedResponse(network, linkId);
	}

	private Map<String, Object> laneUpdatedResponse(Network network, long linkId) {
		return successResponse("lane updated.", "link/edit/"
				+ linkId,
				mapJsonService.getLanesConnectorsJson(network, linkId));
	}

}
