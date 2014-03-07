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
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.model.DriverTypeComposition;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.UserInterfaceException;
import edu.trafficsim.web.service.entity.CompositionService;

@Controller
@RequestMapping(value = "/compositions")
@SessionAttributes(value = { "sequence", "typesFactory", "typesLibrary",
		"odMatrix" })
public class CompositionController extends AbstractController {

	@Autowired
	CompositionService compositionService;

	private static final String DEFAULT_NAME = "New";

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viechleCompositionView(
			@ModelAttribute("typesLibrary") TypesLibrary library, Model model)
			throws ModelInputException, UserInterfaceException {
		model.addAttribute("vehicleCompositions",
				library.getVehicleCompositions());
		model.addAttribute("driverCompositions",
				library.getDriverCompositions());
		return "components/composition";
	}

	@RequestMapping(value = "/vehicle/info/{name}", method = RequestMethod.GET)
	public String vehicleCompositionInfo(@PathVariable String name,
			@ModelAttribute("typesLibrary") TypesLibrary library, Model model) {
		VehicleTypeComposition comp = library.getVehicleComposition(name);

		model.addAttribute("types", library.getVehicleTypes());
		model.addAttribute("composition", comp);
		model.addAttribute("dataType", "vehicle");
		return "components/composition-fragments :: info";
	}

	@RequestMapping(value = "/vehicle/form/{name}", method = RequestMethod.GET)
	public String vehicleCompositionForm(
			@PathVariable String name,
			@MatrixVariable(required = false, defaultValue = "false") boolean isNew,
			@ModelAttribute("typesLibrary") TypesLibrary library, Model model) {
		VehicleTypeComposition comp = library.getVehicleComposition(name);

		model.addAttribute("types", library.getVehicleTypes());
		model.addAttribute("composition", comp);
		model.addAttribute("dataType", "vehicle");
		model.addAttribute("isNew", isNew);
		return "components/composition-fragments :: form";
	}

	@RequestMapping(value = "/vehicle/save", method = RequestMethod.POST)
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

	@RequestMapping(value = "/vehicle/new", method = RequestMethod.POST)
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

	@RequestMapping(value = "/vehicle/remove", method = RequestMethod.POST)
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

	@RequestMapping(value = "/driver/info/{name}", method = RequestMethod.GET)
	public String driverCompositionInfo(@PathVariable String name,
			@ModelAttribute("typesLibrary") TypesLibrary library, Model model) {
		DriverTypeComposition comp = library.getDriverComposition(name);

		model.addAttribute("types", library.getDriverTypes());
		model.addAttribute("composition", comp);
		model.addAttribute("dataType", "driver");
		return "components/composition-fragments :: info";
	}

	@RequestMapping(value = "/driver/form/{name}", method = RequestMethod.GET)
	public String driverCompositionForm(
			@PathVariable String name,
			@MatrixVariable(required = false, defaultValue = "false") boolean isNew,
			@ModelAttribute("typesLibrary") TypesLibrary library, Model model) {
		DriverTypeComposition comp = library.getDriverComposition(name);

		model.addAttribute("types", library.getDriverTypes());
		model.addAttribute("composition", comp);
		model.addAttribute("dataType", "driver");
		model.addAttribute("isNew", isNew);
		return "components/composition-fragments :: form";
	}

	@RequestMapping(value = "/driver/save", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> saveDriverComposition(
			@RequestParam("oldName") String oldName,
			@RequestParam("newName") String newName,
			@RequestParam("types[]") String[] driverTypes,
			@RequestParam("values[]") double[] values,
			@ModelAttribute("typesLibrary") TypesLibrary library) {

		try {
			compositionService.updateDriverComposition(library, oldName,
					newName, driverTypes, values);
		} catch (ModelInputException e) {
			return failureResponse(e);
		} catch (UserInterfaceException e) {
			return failureResponse(e);
		}
		return messageOnlySuccessResponse("Driver Composition updated.");
	}

	@RequestMapping(value = "/driver/new", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> newDriverComposition(
			@ModelAttribute("typesLibrary") TypesLibrary library,
			@ModelAttribute("typesFactory") TypesFactory factory,
			@ModelAttribute("sequence") Sequence seq) {
		try {
			String name = compositionService.createDriverComposition(library,
					factory, seq, DEFAULT_NAME,
					library.getDefaultDriverComposition()).getName();
			return successResponse("Driver Composition created.", null, name);
		} catch (ModelInputException e) {
			return failureResponse(e);
		}

	}

	@RequestMapping(value = "/driver/remove", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> removeDriverComposition(
			@RequestParam("name") String name,
			@ModelAttribute("typesLibrary") TypesLibrary library,
			@ModelAttribute("odMatrix") OdMatrix odMatrix) {
		try {
			compositionService.removeDriverComposition(library, odMatrix, name);
		} catch (UserInterfaceException e) {
			return failureResponse(e);
		}
		return messageOnlySuccessResponse("Driver Composition removed.");
	}

}
