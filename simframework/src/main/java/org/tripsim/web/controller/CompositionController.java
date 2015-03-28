/*
 * Copyright (c) 2015 Xuan Shi
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.a
 * 
 * @author Xuan Shi
 */
package org.tripsim.web.controller;

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
import org.tripsim.api.model.TypesComposition;
import org.tripsim.engine.type.TypesManager;
import org.tripsim.web.service.entity.CompositionService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/compositions")
public class CompositionController extends AbstractController {

	@Autowired
	CompositionService compositionService;
	@Autowired
	TypesManager typesManager;

	private static final String DEFAULT_NAME = "New";

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viechleCompositionView(Model model) {
		model.addAttribute("vehicleCompositions",
				typesManager.getVehicleTypeCompositions());
		model.addAttribute("driverCompositions",
				typesManager.getDriverTypeCompositions());
		return "components/composition";
	}

	@RequestMapping(value = "/vehicle/info/{name}", method = RequestMethod.GET)
	public String vehicleCompositionInfo(@PathVariable String name, Model model) {
		TypesComposition comp = typesManager.getVehicleTypeComposition(name);

		model.addAttribute("composition", comp);
		model.addAttribute("dataType", "vehicle");
		return "components/composition-fragments :: info";
	}

	@RequestMapping(value = "/vehicle/form/{name}", method = RequestMethod.GET)
	public String vehicleCompositionForm(
			@PathVariable String name,
			@MatrixVariable(required = false, defaultValue = "false") boolean isNew,
			Model model) {
		TypesComposition comp = typesManager.getVehicleTypeComposition(name);

		model.addAttribute("types", typesManager.getVehicleTypes());
		model.addAttribute("composition", comp);
		model.addAttribute("dataType", "vehicle");
		model.addAttribute("isNew", isNew);
		return "components/composition-fragments :: form";
	}

	@RequestMapping(value = "/vehicle/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveVehicleComposition(
			@RequestParam("oldName") String oldName,
			@RequestParam("newName") String newName,
			@RequestParam("types[]") String[] vehicleTypes,
			@RequestParam("values[]") double[] values) {
		compositionService.updateVehicleComposition(oldName, newName,
				vehicleTypes, values);
		return successResponse("Vehicle Composition updated.");
	}

	@RequestMapping(value = "/vehicle/new", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> newVehicleComposition() {
		String name = compositionService.createVehicleComposition(
				DEFAULT_NAME + context.getSequence().nextId()).getName();
		return successResponse("Vehicle Composition created.", null, name);
	}

	@RequestMapping(value = "/vehicle/remove", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> removeVehicleComposition(
			@RequestParam("name") String name) {
		compositionService.removeVehicleComposition(name);
		return successResponse("Vehicle Composition removed.");
	}

	@RequestMapping(value = "/driver/info/{name}", method = RequestMethod.GET)
	public String driverCompositionInfo(@PathVariable String name, Model model) {
		TypesComposition comp = typesManager.getDriverTypeComposition(name);

		model.addAttribute("types", typesManager.getDriverTypes());
		model.addAttribute("composition", comp);
		model.addAttribute("dataType", "driver");
		return "components/composition-fragments :: info";
	}

	@RequestMapping(value = "/driver/form/{name}", method = RequestMethod.GET)
	public String driverCompositionForm(
			@PathVariable String name,
			@MatrixVariable(required = false, defaultValue = "false") boolean isNew,
			Model model) {
		TypesComposition comp = typesManager.getDriverTypeComposition(name);

		model.addAttribute("types", typesManager.getDriverTypes());
		model.addAttribute("composition", comp);
		model.addAttribute("dataType", "driver");
		model.addAttribute("isNew", isNew);
		return "components/composition-fragments :: form";
	}

	@RequestMapping(value = "/driver/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveDriverComposition(
			@RequestParam("oldName") String oldName,
			@RequestParam("newName") String newName,
			@RequestParam("types[]") String[] driverTypes,
			@RequestParam("values[]") double[] values) {
		compositionService.updateDriverComposition(oldName, newName,
				driverTypes, values);
		return successResponse("Driver Composition updated.");
	}

	@RequestMapping(value = "/driver/new", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> newDriverComposition() {
		String name = compositionService.createDriverComposition(
				DEFAULT_NAME + context.getSequence().nextId()).getName();
		return successResponse("Driver Composition created.", null, name);

	}

	@RequestMapping(value = "/driver/remove", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> removeDriverComposition(
			@RequestParam("name") String name) {
		compositionService.removeDriverComposition(name);
		return successResponse("Driver Composition removed.");
	}

}
