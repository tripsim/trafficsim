package edu.trafficsim.data.persistence.impl;

import org.springframework.stereotype.Repository;

import edu.trafficsim.data.dom.VehicleDo;
import edu.trafficsim.data.persistence.VehicleDao;

@Repository("vehicle-dao")
class VehicleDaoImpl extends AbstractDaoImpl<VehicleDo> implements VehicleDao {

}
