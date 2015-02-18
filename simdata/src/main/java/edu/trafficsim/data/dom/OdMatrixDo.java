package edu.trafficsim.data.dom;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value = "odmatrices", noClassnameStored = true)
@Indexes({ @Index(value = "name", unique = true), @Index(value = "networkName") })
public class OdMatrixDo {

	@Id
	private ObjectId id;
	private String name;

	private String networkName;

	private long matrixId;
	List<OdDo> ods;

	public long getMatrixId() {
		return matrixId;
	}

	public void setMatrixId(long matrixId) {
		this.matrixId = matrixId;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public List<OdDo> getOds() {
		return ods;
	}

	public void setOds(List<OdDo> ods) {
		this.ods = ods;
	}

}
