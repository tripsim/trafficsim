package org.tripsim.data.dom;

import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value = "compositions", noClassnameStored = true)
@Indexes({ @Index(value = "name", unique = true), @Index(value = "category") })
public class CompositionDo {

	@Id
	private ObjectId id;
	private String name;
	private TypeCategoryDo category;
	private Map<String, Double> composition;
	private boolean defaultComposition;

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

	public Map<String, Double> getComposition() {
		return composition;
	}

	public void setComposition(Map<String, Double> composition) {
		this.composition = composition;
	}

	public boolean isDefaultComposition() {
		return defaultComposition;
	}

	public void setDefaultComposition(boolean defaultComposition) {
		this.defaultComposition = defaultComposition;
	}

}
