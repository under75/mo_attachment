package ru.sartfoms.moattach.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "as_houses", schema = "FIASOWNER")
@IdClass(HouseId.class)
public class House {
	@Column(name = "id")
	private Long id;

	@Column(name = "isactive")
	private Boolean isActive;

	@Column(name = "isactual")
	private Boolean isActual;

	@Column(name = "objectguid")
	private String objectguid;

	@Column(name = "housenum")
	private String houseNum;

	@Column(name = "addnum1")
	private String addNum1;

	@Column(name = "addnum2")
	private String addNum2;
	@Id
	@Column(name = "objectid")
	private Long objectid;
	@Id
	@Column(name = "changeid")
	private Long changeid;
	@Id
	@Column(name = "opertypeid")
	private Integer opertypeid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsActual() {
		return isActual;
	}

	public void setIsActual(Boolean isActual) {
		this.isActual = isActual;
	}

	public String getObjectguid() {
		return objectguid;
	}

	public void setObjectguid(String objectguid) {
		this.objectguid = objectguid;
	}

	public String getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}

	public String getAddNum1() {
		return addNum1;
	}

	public void setAddNum1(String addNum1) {
		this.addNum1 = addNum1;
	}

	public String getAddNum2() {
		return addNum2;
	}

	public void setAddNum2(String addNum2) {
		this.addNum2 = addNum2;
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
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(houseNum != null ? houseNum : "");
		sb.append(addNum1 != null ? " " + addNum1 : "");
		sb.append(addNum2 != null ? " " + addNum2 : "");
		
		return sb.toString();
	}
}
