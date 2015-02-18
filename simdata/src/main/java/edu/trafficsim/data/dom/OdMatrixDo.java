package edu.trafficsim.data.dom;

import java.util.Date;
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
	private Date date;

	private long matrixId;
	private String name;
	private String networkName;

	List<OdDo> ods;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getMatrixId() {
		return matrixId;
	}

	public void setMatrixId(long matrixId) {
		this.matrixId = matrixId;
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
