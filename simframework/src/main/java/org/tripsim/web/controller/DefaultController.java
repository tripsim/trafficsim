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

import javax.servlet.http.HttpSession;

import org.opengis.referencing.FactoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tripsim.engine.demo.DemoBuilder;
import org.tripsim.engine.simulation.SimulationProject;
import org.tripsim.web.Sequence;
import org.tripsim.web.service.MapJsonService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/")
public class DefaultController extends AbstractController {

	@Autowired
	MapJsonService mapJsonService;

	@Autowired
	DemoBuilder demoBuilder;

	@RequestMapping(value = "/")
	public String home(HttpSession session) {
		if (session.isNew()) {
			return "redirect:/new";
		}
		return "index";
	}

	@RequestMapping(value = "/new")
	public String newProject(Model model, HttpSession session) {
		context.clear();
		return "redirect:/";
	}

	@RequestMapping(value = "getdemonetwork", method = RequestMethod.GET)
	public @ResponseBody String demoNetwork(Model model)
			throws FactoryException {
		SimulationProject demo = demoBuilder.getDemo();
		context.setSequence(new Sequence(demo.getNextSeq()));
		context.setNetwork(demo.getNetwork());
		context.setOdMatrix(demo.getOdMatrix());
		return mapJsonService.getNetworkJson(demo.getNetwork());
	}
}
