package ru.sartfoms.moattach.model;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AttachFormParameters {
	@NotNull
	private Integer lpuId;
	@Size(max=50)
	private String phone;
	@Email
	@Size(max=50)
	private String email;
	@Size(max=100)
	@NotEmpty
	private String lpuUnit;
	@Size(max=14)
	@NotEmpty
	private String doctorSnils;
	@NotEmpty
	private String dudlType;
	@Size(max = 12)
	private String dudlSer;
	@Size(max = 30)
	@NotEmpty
	private String dudlNum;
	@NotEmpty
	private String effDate = LocalDate.now().toString();
	private String expDate;
	@Size(max = 200)
	private String dudlPredst;
	@NotNull
	private Integer period = 90;

	public Integer getLpuId() {
		return lpuId;
	}

	public void setLpuId(Integer lpuId) {
		this.lpuId = lpuId;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public void setPhone(String phone) {
		this.phone = phone.trim();
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}

	public String getLpuUnit() {
		return lpuUnit;
	}

	public void setLpuUnit(String lpuUnit) {
		this.lpuUnit = lpuUnit.trim();
	}

	public String getDoctorSnils() {
		return doctorSnils;
	}

	public void setDoctorSnils(String doctorSnils) {
		this.doctorSnils = doctorSnils.trim();
	}

	public String getDudlType() {
		return dudlType;
	}

	public String getDudlSer() {
		return dudlSer;
	}

	public String getDudlNum() {
		return dudlNum;
	}

	public void setDudlType(String dudlType) {
		this.dudlType = dudlType.trim();
	}

	public void setDudlSer(String dudlSer) {
		this.dudlSer = dudlSer;
	}

	public void setDudlNum(String dudlNum) {
		this.dudlNum = dudlNum.trim();
	}

	public String getEffDate() {
		return effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getDudlPredst() {
		return dudlPredst;
	}

	public void setDudlPredst(String dudlPredst) {
		this.dudlPredst = dudlPredst.trim();
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

}
