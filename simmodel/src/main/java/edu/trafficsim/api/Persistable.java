package edu.trafficsim.api;

public interface Persistable extends Nameable {

	boolean isModified();

	void setModified(boolean modified);
}
