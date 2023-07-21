package ru.sartfoms.moattach.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "as_adm_hierarchy", schema = "FIASOWNER")
public class AsAdmHierarchy {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "objectid")
	private Long objectid;

	@Column(name = "parentobjid")
	private Long parentobjid;

	@Column(name = "path_")
	private String path;

	@Column(name = "isactive")
	private Boolean isActive;

	@Column(name = "enddate")
	private String endDate;

	public Long getId() {
		return id;
	}

	public Long getObjectid() {
		return objectid;
	}

	public Long getParentobjid() {
		return parentobjid;
	}

	public String getPath() {
		return path;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public void setParentobjid(Long parentobjid) {
		this.parentobjid = parentobjid;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
