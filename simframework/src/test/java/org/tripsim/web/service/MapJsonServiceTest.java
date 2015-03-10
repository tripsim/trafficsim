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
package org.tripsim.web.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tripsim.api.model.Network;
import org.tripsim.engine.demo.DemoBuilder;
import org.tripsim.engine.network.NetworkFactory;
import org.tripsim.engine.od.OdFactory;
import org.tripsim.engine.simulation.SimulationProject;
import org.tripsim.engine.type.TypesManager;
import org.tripsim.web.service.MapJsonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simframework-test.xml")
public class MapJsonServiceTest {

	Network network;
	MapJsonService jsonService;
	@Autowired
	TypesManager typesManager;
	@Autowired
	NetworkFactory networkFactory;
	@Autowired
	OdFactory odFactory;

	@Autowired
	DemoBuilder demoBuilder;

	@Before
	public void setUp() throws Exception {
		SimulationProject demo = demoBuilder.getDemo();
		jsonService = new MapJsonService();
		network = demo.getNetwork();
	}

	@Test
	public void test() {
		String output;

		output = jsonService.getLinkJson(network, 2);
		System.out.println(output);

		output = jsonService.getLanesJson(network, 2);
		System.out.println(output);

		output = jsonService.getNetworkJson(network);
		System.out.println(output);
	}

}
