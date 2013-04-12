package edu.trafficsim.model;

import org.junit.Test;

import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.network.Lane;
import edu.trafficsim.model.network.Link;
import edu.trafficsim.model.roadusers.Vehicle;

public class LaneTest {

	@Test
	public void testNavigable() throws ModelInputException {
		Link link = new Link(null, null, null, null, null) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public double getLength() {
				return 5;
			}
		};

		Lane lane = new Lane(0, link, 4, 0);
		Vehicle vehicle1 = new Vehicle(null, null, 0);
		vehicle1.setName("first");
		vehicle1.position(10);
		Vehicle vehicle2 = new Vehicle(null, null, 0);
		vehicle2.setName("second");
		vehicle2.position(2);

		lane.add(vehicle1);
		lane.add(vehicle2);

		Vehicle v;
		v = lane.getHeadVehicle();
		System.out.println(v.getName());
		v = lane.getTailVehicle();
		System.out.println(v.getName());

		vehicle1.position(1);
		v = lane.getHeadVehicle();
		System.out.println(v.getName());
		v = lane.getTailVehicle();
		System.out.println(v.getName());
	}
}
