/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.trafficsim.model;

import java.util.Date;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
public abstract class BaseEntity<T> implements ObjectContainer, Comparable<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Date created;

	private Date modified;

	private String createdBy;

	private String modifiedBy;

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

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public Date getModified() {
		return modified;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int compareTo(T o) {
		if (o instanceof BaseEntity)
			return equals(o) ? 0 : (id > ((BaseEntity<?>) o).getId() ? 1 : -1);
		return -1;
	}

}
