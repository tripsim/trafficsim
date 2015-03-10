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
