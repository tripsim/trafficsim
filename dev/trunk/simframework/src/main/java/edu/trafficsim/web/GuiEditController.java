package edu.trafficsim.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.trafficsim.model.Link;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.VehicleTypeComposition;

@Controller
@RequestMapping(value = "/edit")
public class GuiEditController {

	@Autowired
	SimulationProject project;
	@Autowired
	VehicleTypeRepo vehicleTypeRepo;

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

	@RequestMapping(value = "/vehiclecomposition-form/{id}", method = RequestMethod.GET)
	public String vehicleCompositionEdit(@PathVariable long id, Model model) {
		VehicleTypeComposition comp = vehicleTypeRepo
				.getVehicleTypeComposition(id);

		model.addAttribute("vehicleTypes", vehicleTypeRepo.getVehicleTypes());
		model.addAttribute("vehicleComposition", comp);
		return "components/vehiclecomposition-form";
	}

	@RequestMapping(value = "/vehiclecomposition-view/{id}", method = RequestMethod.GET)
	public String vehicleCompositionEditCancel(@PathVariable long id,
			Model model) {
		VehicleTypeComposition comp = vehicleTypeRepo
				.getVehicleTypeComposition(id);

		model.addAttribute("vehicleTypes", vehicleTypeRepo.getVehicleTypes());
		model.addAttribute("vehicleComposition", comp);
		return "components/vehiclecomposition-view";
	}

}
