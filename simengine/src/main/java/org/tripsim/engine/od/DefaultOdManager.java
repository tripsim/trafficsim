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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tripsim.api.model.OdMatrix;
import org.tripsim.data.dom.OdMatrixDo;
import org.tripsim.data.persistence.OdMatrixDao;

@Service("default-od-manager")
class DefaultOdManager implements OdManager {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultOdManager.class);

	@Autowired
	OdMatrixDao odMatrixDao;
	@Autowired
	OdConverter converter;

	@Override
	public void insertOdMatrix(OdMatrix odMatrix) {
		if (odMatrix == null) {
			throw new RuntimeException(
					"OdMatrix is null, cannot be saved to db!");
		}
		if (odMatrixDao.countByName(odMatrix.getName()) != 0) {
			logger.info("OdMatrix '{}' already exists in db!",
					odMatrix.getName());

		}
		OdMatrixDo entity = converter.toOdMatrixDo(odMatrix);
		odMatrixDao.save(entity);
	}

	@Override
	public void saveOdMatrix(OdMatrix odMatrix) {
		if (odMatrix == null) {
			throw new RuntimeException(
					"OdMatrix is null, cannot be saved to db!");
		}
		OdMatrixDo entity = odMatrixDao.findByName(odMatrix.getName());
		if (entity == null) {
			entity = converter.toOdMatrixDo(odMatrix);
		} else {
			converter.applyOdMatrixDo(entity, odMatrix);
		}
		odMatrixDao.save(entity);
	}

	@Override
	public OdMatrix loadOdMatrix(String name) {
		OdMatrixDo odMatrix = odMatrixDao.findByName(name);
		if (odMatrix == null) {
			return null;
		}
		return converter.toOdMatrix(odMatrix);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOdMatrixNames(String networkName) {
		return (List<String>) odMatrixDao.getTypeField("name");
	}

}
