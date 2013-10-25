package edu.trafficsim.engine.factory;

public abstract class AbstractFactory {

	// TODO comply with database id
	private static long id = 0;

	protected long nextId() {
		return id++;
	}
}
