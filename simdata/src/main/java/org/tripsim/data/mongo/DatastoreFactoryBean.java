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
package org.tripsim.data.mongo;

import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;

@Component("datastore")
public class DatastoreFactoryBean extends
		AbstractFactoryBean<AdvancedDatastore> {

	@Value("${mongo.db.name}")
	private String dbName;
	@Value("${mongo.db.ensure.indexes:true}")
	private boolean ensureIndexes;

	@Autowired
	private Morphia morphia;
	@Autowired
	private MongoClient mongoClient;

	@Override
	public Class<?> getObjectType() {
		return AdvancedDatastore.class;
	}

	@Override
	protected AdvancedDatastore createInstance() throws Exception {
		try {
			DatastoreImpl ds = (DatastoreImpl) morphia.createDatastore(
					mongoClient, dbName);
			ds.ensureCaps();
			ds.ensureIndexes(ensureIndexes);
			return ds;
		} catch (MongoException me) {
			logger.error(String.format(
					"Error encountered starting up datastore: (%s) %s", me
							.getClass().getSimpleName(), me.getMessage()));
			throw new RuntimeException(me);
		}
	}

}
