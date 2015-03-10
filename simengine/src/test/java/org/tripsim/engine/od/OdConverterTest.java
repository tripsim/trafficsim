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
package org.tripsim.engine.od;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.data.dom.OdMatrixDo;
import org.tripsim.engine.demo.DemoBuilder;
import org.tripsim.engine.od.OdConverter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simengine-test.xml")
public class OdConverterTest {

	Logger logger = LoggerFactory.getLogger(OdConverterTest.class);
	@Autowired
	DemoBuilder demoBuilder;
	@Autowired
	OdConverter converter;

	@Test
	public void test() {
		OdMatrix odMatrix = demoBuilder.getDemo().getOdMatrix();
		OdMatrixDo entity = converter.toOdMatrixDo(odMatrix);
		logger.info("{}", entity);
		OdMatrix newOdMatrix = converter.toOdMatrix(entity);
		logger.info("{}", newOdMatrix);
	}

}
