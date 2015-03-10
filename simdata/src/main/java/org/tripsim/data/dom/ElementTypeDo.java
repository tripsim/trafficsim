package org.tripsim.data.dom;

import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value = "elementTypes", noClassnameStored = true)
@Indexes({ @Index(value = "name", unique = true), @Index(value = "category") })
public class ElementTypeDo {

	@Id
	private ObjectId id;
	private String name;
	private TypeCategoryDo category;
	private boolean defaultType;
	private Map<String, Object> properties;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeCategoryDo getCategory() {
		return category;
	}

	public void setCategory(TypeCategoryDo category) {
		this.category = category;
	}

	public boolean isDefaultType() {
		return defaultType;
	}

	public void setDefaultType(boolean defaultType) {
		this.defaultType = defaultType;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
}
