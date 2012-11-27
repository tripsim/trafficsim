package edu.trafficsim.model.core;

import java.util.Date;

import edu.trafficsim.model.DataContainer;
import edu.trafficsim.model.core.User;


@SuppressWarnings("unused")
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
}
