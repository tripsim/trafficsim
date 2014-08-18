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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.trafficsim.model.Network;
import edu.trafficsim.web.service.MapJsonService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/map")
@SessionAttributes(value = { "network" })
public class MapController extends AbstractController {

	@Autowired
	MapJsonService mapJsonService;

	@RequestMapping(value = "/network", method = RequestMethod.GET)
	public @ResponseBody
	String getNetwork(@ModelAttribute("network") Network network) {
		String str = mapJsonService.getNetworkJson(network);
		return str;
	}

	@RequestMapping(value = "/center", method = RequestMethod.GET)
	public @ResponseBody
	String getCenter(@ModelAttribute("network") Network network) {
		String str = mapJsonService.getCenterJson(network);
		return str;
	}

	@RequestMapping(value = "/link/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String getLink(@PathVariable long id,
			@ModelAttribute("network") Network network) {
		String str = mapJsonService.getLinkJson(network, id);
		return str;
	}

	@RequestMapping(value = "/lanes/{linkId}", method = RequestMethod.GET)
	public @ResponseBody
	String getLanes(@PathVariable long linkId,
			@ModelAttribute("network") Network network) {
		String str = mapJsonService.getLanesJson(network, linkId);
		return str;
	}

}
