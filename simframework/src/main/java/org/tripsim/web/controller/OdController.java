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
package org.tripsim.web.controller;

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
import org.tripsim.api.model.Network;
import org.tripsim.api.model.Node;
import org.tripsim.api.model.Od;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.engine.type.TypesManager;
import org.tripsim.web.Sequence;
import org.tripsim.web.model.OdCandidates;
import org.tripsim.web.service.entity.OdService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/od")
@SessionAttributes(value = { "sequence", "network", "odMatrix",
		"candidateOdMatrix" })
public class OdController extends AbstractController {

	@Autowired
	OdService odService;
	@Autowired
	TypesManager typesManager;

	@Autowired
	OdCandidates candidates;

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
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix, Model model) {
		Od od = isNew ? candidates.get(id) : odMatrix.getOd(id);
		if (od == null) {
			throw new IllegalArgumentException("no such od!");
		}

		Node origin = network.getNode(od.getOriginNodeId());
		// TODO make it reachable sinks
		model.addAttribute("vehicleCompositions",
				typesManager.getDefaultVehicleTypeComposition());
		model.addAttribute("driverCompositions",
				typesManager.getDefaultDriverTypeComposition());
		model.addAttribute("destinations", network.getSinks(origin));
		model.addAttribute("od", od);
		model.addAttribute("isNew", isNew);
		return "components/od-fragments :: form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveOd(
			@RequestParam("id") long id,
			@RequestParam("destinatioId") long dId,
			@RequestParam("vehicleCompositionName") String vcName,
			@RequestParam("driverCompositionName") String dcName,
			@RequestParam("times[]") double[] times,
			@RequestParam("vphs[]") Integer[] vphs,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix) {
		Od od = candidates.remove(id);
		if (od != null) {
			try {
				odService.addOd(odMatrix, network, od, dId, vcName, dcName,
						times, vphs);
			} catch (RuntimeException e) {
				candidates.add(od);
				throw e;
			}
		} else {
			odService.updateOd(odMatrix, network, id, dId, vcName, dcName,
					times, vphs);
		}
		return successResponse("Od saved.");
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> newOd(
			@RequestParam("originId") long oid,
			@ModelAttribute("sequence") Sequence sequence,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix) {
		Node origin = network.getNode(oid);
		Od od = odService.createOd(sequence, odMatrix, origin, null);
		candidates.add(od);
		return successResponse("New od created.", null, od.getId());
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> removeOd(
			@RequestParam("id") long id,
			@ModelAttribute("odMatrix") OdMatrix odMatrix) {
		candidates.remove(id);
		odService.removeOd(odMatrix, id);
		return successResponse("Od removed.");
	}

}
