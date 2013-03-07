package edu.trafficsim.factory;

public class RoadUserFactory extends AbstractFactory {
	
	private static RoadUserFactory factory;
	
	private RoadUserFactory() {}
	
	public static RoadUserFactory getInstance() {
		if (factory == null)
			factory = new RoadUserFactory();
		return factory;
	}
	
}
