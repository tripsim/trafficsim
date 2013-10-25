package edu.trafficsim.model.core;

import java.util.Date;

import edu.trafficsim.model.DataContainer;

public abstract class BaseEntity<T> implements DataContainer, Comparable<T> {

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

	public BaseEntity(long id, String name) {
		this.id = id;
		this.name = name;
		this.description = null;
	}

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

	@Override
	public String toString() {
		return getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(T o) {
		if (o instanceof BaseEntity)
			return equals(o) ? 0 : (id > ((BaseEntity<?>) o).getId() ? 1 : -1);
		return -1;
	}

}
