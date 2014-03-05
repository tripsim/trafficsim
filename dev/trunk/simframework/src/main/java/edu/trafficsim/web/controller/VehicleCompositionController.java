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

import edu.trafficsim.engine.TypesFactory;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.utility.Sequence;
import edu.trafficsim.web.UserInterfaceException;
import edu.trafficsim.web.service.entity.CompositionService;

@Controller
@RequestMapping(value = "/vehiclecomposition")
@SessionAttributes(value = { "sequence", "typesFactory", "typesLibrary",
		"odMatrix" })
public class VehicleCompositionController extends AbstractController {

	@Autowired
	CompositionService compositionService;

	private static final String DEFAULT_NAME = "New";

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viechleCompositionView(
			@ModelAttribute("typesLibrary") TypesLibrary library, Model model)
			throws ModelInputException, UserInterfaceException {
		model.addAttribute("vehicleCompositions",
				library.getVehicleCompositions());
		return "components/vehiclecomposition";
	}

	@RequestMapping(value = "/info/{name}", method = RequestMethod.GET)
	public String vehicleCompositionEditCancel(@PathVariable String name,
			@ModelAttribute("typesLibrary") TypesLibrary library, Model model) {
		VehicleTypeComposition comp = library.getVehicleComposition(name);

		model.addAttribute("vehicleTypes", library.getVehicleTypes());
		model.addAttribute("vehicleComposition", comp);
		return "components/vehiclecomposition-fragments :: info";
	}

	@RequestMapping(value = "/form/{name}", method = RequestMethod.GET)
	public String vehicleCompositionEdit(
			@PathVariable String name,
			@MatrixVariable(required = false, defaultValue = "false") boolean isNew,
			@ModelAttribute("typesLibrary") TypesLibrary library, Model model) {
		VehicleTypeComposition comp = library.getVehicleComposition(name);

		model.addAttribute("vehicleTypes", library.getVehicleTypes());
		model.addAttribute("vehicleComposition", comp);
		model.addAttribute("isNew", isNew);
		return "components/vehiclecomposition-fragments :: form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> saveVehicleComposition(
			@RequestParam("oldName") String oldName,
			@RequestParam("newName") String newName,
			@RequestParam("types[]") String[] vehicleTypes,
			@RequestParam("values[]") double[] values,
			@ModelAttribute("typesLibrary") TypesLibrary library) {

		try {
			compositionService.updateVehicleComposition(library, oldName,
					newName, vehicleTypes, values);
		} catch (ModelInputException e) {
			return failureResponse(e);
		} catch (UserInterfaceException e) {
			return failureResponse(e);
		}
		return messageOnlySuccessResponse("Vehicle Composition updated.");
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> newVehicleComposition(
			@ModelAttribute("typesLibrary") TypesLibrary library,
			@ModelAttribute("typesFactory") TypesFactory factory,
			@ModelAttribute("sequence") Sequence seq) {
		try {
			String name = compositionService.createVehicleComposition(library,
					factory, seq, DEFAULT_NAME,
					library.getDefaultVehicleComposition()).getName();
			return successResponse("Vehicle Composition created.", null, name);
		} catch (ModelInputException e) {
			return failureResponse(e);
		}

	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> removeVehicleComposition(
			@RequestParam("name") String name,
			@ModelAttribute("typesLibrary") TypesLibrary library,
			@ModelAttribute("odMatrix") OdMatrix odMatrix) {
		try {
			compositionService
					.removeVehicleComposition(library, odMatrix, name);
		} catch (UserInterfaceException e) {
			return failureResponse(e);
		}
		return messageOnlySuccessResponse("Vehicle Composition removed.");
	}
}
