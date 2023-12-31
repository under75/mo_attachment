package ru.sartfoms.moattach.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ЛПУ", schema = "LPUOWNER")
public class Lpu implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CD_LPU")
	private Integer id;
	
	@Column(name = "NM_LPU")
	private String name;
	
	@Column(name="M_NAMEF")
	private String fullName;
	
	@Column(name = "mcod")
	private String moСode;
	
	@Column(name = "CD_LPUIN")
	private Integer parentId;
	
	@Column(name = "FLAG_M")
	private Integer flagM;
	
	@Column(name = "FLAG_A")
	private Integer flagA;
	
	@Column(name = "fiogv")
	private String chiefDoctor;
	
	@Column(name = "adr")
	private String address;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMoСode() {
		return moСode;
	}
	public void setMoСode(String moСode) {
		this.moСode = moСode;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getFlagM() {
		return flagM;
	}
	public Integer getFlagA() {
		return flagA;
	}
	public void setFlagM(Integer flagM) {
		this.flagM = flagM;
	}
	public void setFlagA(Integer flagA) {
		this.flagA = flagA;
	}
	public String getChiefDoctor() {
		return chiefDoctor;
	}
	public void setChiefDoctor(String chiefDoctor) {
		this.chiefDoctor = chiefDoctor;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
