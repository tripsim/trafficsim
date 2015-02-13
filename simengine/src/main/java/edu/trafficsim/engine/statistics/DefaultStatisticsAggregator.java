package edu.trafficsim.engine.statistics;


class DefaultStatisticsAggregator implements StatisticsAggregator {

	
	
//	@Override
//	public List<StatisticsFrame> getFrames() {
//		return Collections.unmodifiableList(frames);
//	}
//
//	@Override
//	public VehicleState[] trajectory(Long vid) {
//		List<VehicleState> trajectory = new ArrayList<VehicleState>();
//		for (StatisticsFrame frame : frames) {
//			VehicleState state = frame.getVehicleState(vid);
//			if (state == null)
//				continue;
//			trajectory.add(state);
//		}
//		return trajectory.toArray(new VehicleState[0]);
//	}
//
//	@Override
//	public LinkState[] linkStats(Long id) {
//		List<LinkState> linkStats = new ArrayList<LinkState>();
//		for (StatisticsFrame frame : frames) {
//			LinkState state = frame.getLinkState(id);
//			if (state == null)
//				continue;
//			linkStats.add(state);
//		}
//		return linkStats.toArray(new LinkState[0]);
//	}
//
//	@Override
//	public Map<Long, List<VehicleState>> trajectories() {
//		Map<Long, List<VehicleState>> trajectories = new HashMap<Long, List<VehicleState>>();
//		for (StatisticsFrame frame : frames) {
//			for (Long vid : frame.getVehicleIds()) {
//				List<VehicleState> trajectory = trajectories.get(vid);
//				if (trajectory == null)
//					trajectories.put(vid,
//							trajectory = new ArrayList<VehicleState>());
//				trajectory.add(frame.getVehicleState(vid));
//			}
//		}
//		return trajectories;
//	}
}
