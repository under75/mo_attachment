package ru.sartfoms.moattach.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "as_addr_obj", schema = "FIASOWNER")
public class AsAddrObj {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "isactive")
	private Boolean isActive;

	@Column(name = "isactual")
	private Boolean isActual;
	
	@Column(name = "objectid")
	private Long objectid;

	@Column(name = "objectguid")
	private String objectguid;

	@Column(name = "name_")
	private String name;

	@Column(name = "typename")
	private String typename;
	
	@Column(name = "level_")
	private Integer level;


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

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public String getObjectguid() {
		return objectguid;
	}

	public void setObjectguid(String objectguid) {
		this.objectguid = objectguid.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename.trim();
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name != null ? name : "");
		sb.append(typename != null ? " " + typename : "");

		return sb.toString();
	}

}
