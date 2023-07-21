package ru.sartfoms.moattach.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ATTACH_OTHERREGIONS_HIST", schema = "LPUOWNER")
public class AttachOtherRegionsHist {
	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "attachId")
	private Long attachid;
	@Column(name = "aoguid")
	private String aoguid;
	@Column(name = "hsguid")
	private String hsguid;
	@Column(name = "phone")
	private String phone;
	@Column(name = "email")
	private String email;
	@Column(name = "lpuid")
	private Long lpuId;
	@Column(name = "lpuunit")
	private String lpuUnit;
	@Column(name = "doctorsnils")
	private String doctorSnils;
	@Column(name = "editdate")
	private LocalDate editDate;

	public Long getId() {
		return id;
	}

	public Long getAttachid() {
		return attachid;
	}

	public String getAoguid() {
		return aoguid;
	}

	public String getHsguid() {
		return hsguid;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public Long getLpuId() {
		return lpuId;
	}

	public String getLpuUnit() {
		return lpuUnit;
	}

	public String getDoctorSnils() {
		return doctorSnils;
	}

	public LocalDate getEditDate() {
		return editDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAttachid(Long attachid) {
		this.attachid = attachid;
	}

	public void setAoguid(String aoguid) {
		this.aoguid = aoguid;
	}

	public void setHsguid(String hsguid) {
		this.hsguid = hsguid;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLpuId(Long lpuId) {
		this.lpuId = lpuId;
	}

	public void setLpuUnit(String lpuUnit) {
		this.lpuUnit = lpuUnit;
	}

	public void setDoctorSnils(String doctorSnils) {
		this.doctorSnils = doctorSnils;
	}

	public void setEditDate(LocalDate editDate) {
		this.editDate = editDate;
	}

}
