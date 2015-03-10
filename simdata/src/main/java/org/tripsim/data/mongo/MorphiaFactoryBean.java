package org.tripsim.data.mongo;

import java.util.List;

import javax.annotation.Resource;

import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

@Component("morphia")
public class MorphiaFactoryBean extends AbstractFactoryBean<Morphia> {

	@Resource(name = "morphia.map.packages")
	private List<String> mapPackages;
	@Resource(name = "morphia.map.classes")
	private List<String> mapClasses;
	@Value("${morphia.ignore.invalid.classes:false}")
	private boolean ignoreInvalidClasses;

	@Override
	public Class<?> getObjectType() {
		return Morphia.class;
	}

	@Override
	protected Morphia createInstance() throws Exception {
		Morphia m = new Morphia();
		if (mapPackages != null) {
			for (String packageName : mapPackages) {
				m.mapPackage(packageName, ignoreInvalidClasses);
			}
		}
		if (mapClasses != null) {
			for (String entityClass : mapClasses) {
				m.map(Class.forName(entityClass));
			}
		}
		return m;
	}

}
