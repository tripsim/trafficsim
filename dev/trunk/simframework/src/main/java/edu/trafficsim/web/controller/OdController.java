package edu.trafficsim.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.engine.OdFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.UserInterfaceException;
import edu.trafficsim.web.service.entity.OdService;

@Controller
@RequestMapping(value = "/od")
@SessionAttributes(value = { "sequence", "typesLibrary", "odFactory",
		"network", "odMatrix" })
public class OdController extends AbstractController {

	@Autowired
	OdService odService;

	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	public String odView(@PathVariable long id,
			@ModelAttribute("odMatrix") OdMatrix odMatrix, Model model) {
		Od od = odMatrix.getOd(id);

		model.addAttribute("od", od);
		return "components/od-fragments :: info";
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public String odEdit(
			@PathVariable long id,
			@MatrixVariable(required = false, defaultValue = "false") boolean isNew,
			@ModelAttribute("typesLibrary") TypesLibrary library,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix, Model model) {

		Od od = odMatrix.getOd(id);
		// TODO make it reachable sinks
		model.addAttribute("vehicleCompositions",
				library.getVehicleCompositions());
		model.addAttribute("driverCompositions",
				library.getDriverCompositions());
		model.addAttribute("destinations", network.getSinks());
		model.addAttribute("od", od);
		model.addAttribute("isNew", isNew);
		return "components/od-fragments :: form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> saveOd(@RequestParam("id") long id,
			@RequestParam("destinatioId") long dId,
			@RequestParam("vehicleCompositionName") String vcName,
			@RequestParam("driverCompositionName") String dcName,
			@RequestParam("times[]") double[] times,
			@RequestParam("vphs[]") Integer[] vphs,
			@ModelAttribute("typesLibrary") TypesLibrary library,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix) {
		try {
			odService.updateOd(library, network, odMatrix, id, dId, vcName,
					dcName, times, vphs);
			return messageOnlySuccessResponse("Od saved.");
		} catch (ModelInputException e) {
			return failureResponse(e);
		}
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> newOd(@RequestParam("originId") long oid,
			@ModelAttribute("typesLibrary") TypesLibrary library,
			@ModelAttribute("odFactory") OdFactory factory,
			@ModelAttribute("sequence") Sequence sequence,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix) {

		Node origin = network.getNode(oid);
		try {
			long id = odService.createOd(library, factory, sequence, odMatrix,
					origin, null).getId();

			return successResponse("New od created.", null, id);
		} catch (ModelInputException | UserInterfaceException e) {
			return failureResponse(e.getMessage());
		}
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> removeOd(@RequestParam("id") long id,
			@ModelAttribute("odMatrix") OdMatrix odMatrix) {
		odService.removeOd(odMatrix, id);
		return messageOnlySuccessResponse("Od removed.");
	}

}
