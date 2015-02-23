/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
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

import edu.trafficsim.engine.type.DriverType;
import edu.trafficsim.engine.type.TypesManager;
import edu.trafficsim.engine.type.VehicleType;
import edu.trafficsim.model.VehicleClass;
import edu.trafficsim.web.Sequence;
import edu.trafficsim.web.service.entity.TypesService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/types")
@SessionAttributes(value = { "sequence" })
public class TypesController extends AbstractController {

	private static final String DEFAULT_NAME = "Type";
	private static final VehicleClass DEFAULT_VEHICLECLASS = VehicleClass.Car;

	@Autowired
	TypesManager typesManager;
	@Autowired
	TypesService typesService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewTypes(Model model) {
		model.addAttribute("driverTypes", typesManager.getDriverTypes());
		model.addAttribute("vehicleTypes", typesManager.getVehicleTypes());
		return "components/types";
	}

	@RequestMapping(value = "/vehicle/{name}", method = RequestMethod.GET)
	public String editVehicleType(
			@PathVariable String name,
			@MatrixVariable(required = false, defaultValue = "false") boolean isNew,
			Model model) {
		model.addAttribute("isNew", isNew);
		model.addAttribute("vehicleType", typesManager.getVehicleType(name));
		return "components/vehicletype";
	}

	@RequestMapping(value = "/vehicle/new", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> newVehicleType(
			@ModelAttribute("sequence") edu.trafficsim.web.Sequence sequence) {
		VehicleType type = typesService.createVehicleType(DEFAULT_NAME
				+ sequence.nextId(), DEFAULT_VEHICLECLASS);
		return successResponse("Vehicle type created",
				"types/vehicle/" + type.getName() + ";isNew=true");
	}

	@RequestMapping(value = "/vehicle/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveVehicleType(
			@RequestParam("name") String name,
			@RequestParam("newName") String newName,
			@RequestParam("vehicleClass") VehicleClass vehicleClass,
			@RequestParam("width") double width,
			@RequestParam("length") double length,
			@RequestParam("maxAccel") double maxAccel,
			@RequestParam("maxDecel") double maxDecel,
			@RequestParam("maxSpeed") double maxSpeed) {
		typesService.updateVehicleType(name, newName, vehicleClass, width,
				length, maxAccel, maxDecel, maxSpeed);
		return successResponse("Vehicle type updated.");
	}

	@RequestMapping(value = "/vehicle/remove", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> removeVehicleType(
			@RequestParam("name") String name) {
		typesService.removeVehicleType(name);
		return successResponse("Vehicle type removed", "types/view/");
	}

	@RequestMapping(value = "/driver/{name}", method = RequestMethod.GET)
	public String editDriverType(
			@PathVariable String name,
			@MatrixVariable(required = false, defaultValue = "false") boolean isNew,
			Model model) {
		model.addAttribute("isNew", isNew);
		model.addAttribute("driverType", typesManager.getDriverType(name));
		return "components/drivertype";
	}

	@RequestMapping(value = "/driver/new", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> newDriverType(
			@ModelAttribute("sequence") Sequence sequence) {

		DriverType type = typesService.createDriverType(DEFAULT_NAME
				+ sequence.nextId());
		return successResponse("Driver type created",
				"types/driver/" + type.getName() + ";isNew=true");
	}

	@RequestMapping(value = "/driver/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveDriverType(
			@RequestParam("name") String name,
			@RequestParam("newName") String newName,
			@RequestParam("perceptionTime") double perceptionTime,
			@RequestParam("reactionTime") double reactionTime,
			@RequestParam("desiredHeadway") double desiredHeadway,
			@RequestParam("desiredSpeed") double desiredSpeed) {

		typesService.updateDriverType(name, newName, perceptionTime,
				reactionTime, desiredHeadway, desiredSpeed);
		return successResponse("Driver type updated.");
	}

	@RequestMapping(value = "/driver/remove", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> removeDriverType(
			@RequestParam("name") String name) {
		typesService.removeDriverType(name);
		return successResponse("Driver type removed", "types/view/");
	}

}
