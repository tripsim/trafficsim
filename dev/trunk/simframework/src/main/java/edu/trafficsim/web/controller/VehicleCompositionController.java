package edu.trafficsim.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.trafficsim.model.VehicleTypeComposition;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.UserInterfaceException;
import edu.trafficsim.web.service.entity.CompositionService;

@Controller
@RequestMapping(value = "/vehiclecomposition")
public class VehicleCompositionController extends AbstractController {

	@Autowired
	CompositionService compositionService;

	private static final String DEFAULT_NAME = "New";

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viechleCompositionView(Model model)
			throws ModelInputException, UserInterfaceException {
		model.addAttribute("vehicleCompositions",
				project.getVehicleCompositions());
		return "components/vehiclecomposition";
	}

	@RequestMapping(value = "/info/{name}", method = RequestMethod.GET)
	public String vehicleCompositionEditCancel(@PathVariable String name,
			Model model) {
		VehicleTypeComposition comp = project.getVehicleComposition(name);

		model.addAttribute("vehicleTypes", project.getVehicleTypes());
		model.addAttribute("vehicleComposition", comp);
		return "components/vehiclecomposition-info";
	}

	@RequestMapping(value = "/form/{name}", method = RequestMethod.GET)
	public String vehicleCompositionEdit(
			@PathVariable String name,
			@MatrixVariable(required = false, defaultValue = "false") boolean isNew,
			Model model) {
		VehicleTypeComposition comp = project.getVehicleComposition(name);

		model.addAttribute("vehicleTypes", project.getVehicleTypes());
		model.addAttribute("vehicleComposition", comp);
		model.addAttribute("isNew", isNew);
		return "components/vehiclecomposition-form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> saveVehicleComposition(
			@RequestParam("oldName") String oldName,
			@RequestParam("newName") String newName,
			@RequestParam("types[]") String[] vehicleTypes,
			@RequestParam("values[]") double[] values) {

		try {
			compositionService.updateVehicleComposition(oldName, newName,
					vehicleTypes, values);
		} catch (ModelInputException e) {
			return failureResponse(e.getMessage());
		} catch (UserInterfaceException e) {
			return failureResponse(e.getMessage());
		}
		return messageOnlySuccessResponse("Vehicle Composition updated.");
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> newVehicleComposition() {
		try {
			String name = compositionService.createVehicleComposition(
					DEFAULT_NAME).getName();
			return successResponse("Vehicle Composition created.", null, name);
		} catch (ModelInputException | UserInterfaceException e) {
			return failureResponse(e.getMessage());
		}

	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> removeVehicleComposition(
			@RequestParam("name") String name) {
		compositionService.removeVehicleComposition(name);
		return messageOnlySuccessResponse("Vehicle Composition removed.");
	}
}
