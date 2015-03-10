package org.tripsim.data.dom;

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
	private Date timestamp;

	private String name;
	private String networkName;

	List<OdDo> ods;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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

	@Override
	public String toString() {
		return "OdMatrixDo [id=" + id + ", timestamp=" + timestamp + ", name="
				+ name + ", networkName=" + networkName + ", ods=" + ods + "]";
	}

}
