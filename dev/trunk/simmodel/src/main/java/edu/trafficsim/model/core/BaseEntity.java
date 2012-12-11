package edu.trafficsim.model.core;

import java.util.Date;

import edu.trafficsim.model.DataContainer;
import edu.trafficsim.model.core.User;

public abstract class BaseEntity<T> implements DataContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Date created;
	
	private Date modified;
	
	private User createdBy;
	
	private User modifiedBy;
	
	private String name;
	
	private String description;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public Date getCreated() {
		return created;
	}

	public Date getModified() {
		return modified;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}
}
