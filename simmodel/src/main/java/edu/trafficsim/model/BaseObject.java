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

import java.io.Serializable;
import java.util.Date;

import edu.trafficsim.api.Identifiable;

/**
 * 
 * 
 * @author Xuan Shi
 * @param <T>
 *            the generic type
 */
@SuppressWarnings("rawtypes")
public abstract class BaseObject implements Identifiable, Comparable,
		Serializable {

	private static final long serialVersionUID = 1L;

	protected static final long INTERNAL_USE_ID = -1;

	private long id;

	private Date created;

	private Date modified;

	private String createdBy;

	private String modifiedBy;

	private String description;

	public BaseObject(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return getClass().getSimpleName() + " [id=" + id + "]";
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof BaseObject)
			return equals(o) ? 0 : (id > ((BaseObject) o).getId() ? 1 : -1);
		return -1;
	}

}
