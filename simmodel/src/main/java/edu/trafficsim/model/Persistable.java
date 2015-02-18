package edu.trafficsim.model;

public interface Persistable {

	boolean isModified();

	void setModified(boolean modified);
}
