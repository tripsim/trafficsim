package edu.trafficsim.data.persistence;

import org.junit.runner.RunWith;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.MongoClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/db-test.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractMongoTest {

	@Autowired
	AdvancedDatastore dataStore;
	@Autowired
	MongoClient mongoClient;
	@Autowired
	Morphia morphia;
}
