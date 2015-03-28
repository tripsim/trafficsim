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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tripsim.web.service.MapJsonService;

/**
 * 
 * 
 * @author Xuan Shi
 */
@Controller
@RequestMapping(value = "/map")
public class MapController extends AbstractController {

	@Autowired
	MapJsonService mapJsonService;

	@RequestMapping(value = "/network", method = RequestMethod.GET)
	public @ResponseBody String getNetwork() {
		String str = mapJsonService.getNetworkJson(context.getNetwork());
		return str;
	}

	@RequestMapping(value = "/center", method = RequestMethod.GET)
	public @ResponseBody String getCenter() {
		String str = mapJsonService.getCenterJson(context.getNetwork());
		return str;
	}

	@RequestMapping(value = "/link/{id}", method = RequestMethod.GET)
	public @ResponseBody String getLink(@PathVariable long id) {
		String str = mapJsonService.getLinkJson(context.getNetwork(), id);
		return str;
	}

	@RequestMapping(value = "/lanes/{linkId}", method = RequestMethod.GET)
	public @ResponseBody String getLanes(@PathVariable long linkId) {
		String str = mapJsonService.getLanesJson(context.getNetwork(), linkId);
		return str;
	}

}
