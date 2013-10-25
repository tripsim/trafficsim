package edu.trafficsim.model;

import org.junit.Test;

import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.model.network.DefaultLane;
import edu.trafficsim.model.network.DefaultLink;
import edu.trafficsim.model.roadusers.DefaultVehicle;
import edu.trafficsim.model.roadusers.VehicleType;
import edu.trafficsim.model.roadusers.VehicleType.VehicleClass;

public class LaneTest {

	@Test
	public void testNavigable() throws ModelInputException {
		DefaultLink link = new DefaultLink(0, null, null, null, null, null) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public double getLength() {
				return 5;
			}
		};

		Lane lane = new DefaultLane(0, link, 4, 0, 0);
		VehicleType vehicleType = new VehicleType(0, "Test", VehicleClass.Car);
		DefaultVehicle vehicle1 = new DefaultVehicle(0, vehicleType, null, 0);
		vehicle1.setName("first");
		vehicle1.position(10);
		DefaultVehicle vehicle2 = new DefaultVehicle(1, vehicleType, null, 0);
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
