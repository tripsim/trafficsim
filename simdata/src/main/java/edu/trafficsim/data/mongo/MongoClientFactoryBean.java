package edu.trafficsim.data.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Component("mongo")
public class MongoClientFactoryBean extends AbstractFactoryBean<MongoClient> {

	@Value("${mongo.db.addresses}")
	private String[] addresses;
	@Value("${mongo.db.name:test}")
	private String dbName;
	@Value("${mongo.db.username}")
	private String username;
	@Value("${mongo.db.password}")
	private String password;

	@Override
	public Class<?> getObjectType() {
		return MongoClient.class;
	}

	@Override
	protected MongoClient createInstance() throws Exception {
		return new MongoClient(getServerAddresses(), getCredentials());
	}

	private List<ServerAddress> getServerAddresses() {
		List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
		try {
			for (String address : new HashSet<String>(Arrays.asList(addresses))) {
				String[] str = address.split(":");
				String host = str[0];
				if (str.length > 2) {
					throw new IllegalArgumentException(
							"Invalid server address: " + address);
				} else if (str.length == 2) {
					serverAddresses.add(new ServerAddress(host, Integer
							.parseInt(str[1])));
				} else {
					serverAddresses.add(new ServerAddress(host));
				}
			}
			if (serverAddresses.isEmpty()) {
				throw new IllegalArgumentException(
						"No server address is provided!");
			}
			return serverAddresses;
		} catch (Exception e) {
			throw new BeanCreationException(
					"Error while creating serverAddresses", e);
		}
	}

	private List<MongoCredential> getCredentials() {
		return StringUtils.hasText(username) ? Arrays.asList(MongoCredential
				.createMongoCRCredential(username, dbName,
						password.toCharArray())) : null;
	}
}
