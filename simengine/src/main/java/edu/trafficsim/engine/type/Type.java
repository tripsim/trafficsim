package edu.trafficsim.engine.type;

public abstract class Type {

	private String name;

	private boolean defaultType;

	Type(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDefaultType() {
		return defaultType;
	}

	public void setDefaultType(boolean defaultType) {
		this.defaultType = defaultType;
	}

}
