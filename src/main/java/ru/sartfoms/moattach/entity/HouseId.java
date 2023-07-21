package ru.sartfoms.moattach.entity;

import java.io.Serializable;
import java.util.Objects;

public class HouseId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long objectid;

	private Long changeid;

	private Integer opertypeid;

	public HouseId() {
	}

	public HouseId(Long objectid, Long changeid, Integer opertypeid) {
		super();
		this.objectid = objectid;
		this.changeid = changeid;
		this.opertypeid = opertypeid;
	}

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public Long getChangeid() {
		return changeid;
	}

	public void setChangeid(Long changeid) {
		this.changeid = changeid;
	}

	public Integer getOpertypeid() {
		return opertypeid;
	}

	public void setOpertypeid(Integer opertypeid) {
		this.opertypeid = opertypeid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(changeid, objectid, opertypeid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HouseId other = (HouseId) obj;
		return Objects.equals(changeid, other.changeid) && Objects.equals(objectid, other.objectid)
				&& Objects.equals(opertypeid, other.opertypeid);
	}

}
