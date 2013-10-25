package edu.trafficsim.engine.factory;

public abstract class AbstractFactory {
	
	private static long id = 0;

	protected long nextId() {
		return id++;
	}
}
