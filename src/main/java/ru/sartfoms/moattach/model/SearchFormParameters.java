package ru.sartfoms.moattach.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SearchFormParameters {
	@NotNull
	private Integer moId;
	private Integer lpuId;
	@Size(max = 100)
	private String lpuUnit;
	private String doctorSnils;
	private String effDate;
	private String expDate;
	@Size(max = 40)
	private String lastName;
	@Size(max = 40)
	private String firstName;
	@Size(max = 40)
	private String patronymic;
	private String birthDay;
	@Size(max = 16)
	private String policyNum;
	private Boolean historical = false;

	public Integer getMoId() {
		return moId;
	}

	public Integer getLpuId() {
		return lpuId;
	}

	public String getLpuUnit() {
		return lpuUnit;
	}

	public String getDoctorSnils() {
		return doctorSnils;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public String getPolicyNum() {
		return policyNum;
	}

	public void setMoId(Integer moId) {
		this.moId = moId;
	}

	public void setLpuId(Integer lpuId) {
		this.lpuId = lpuId;
	}

	public void setLpuUnit(String lpuUnit) {
		if (lpuUnit != null)
			this.lpuUnit = lpuUnit.trim();
	}

	public void setDoctorSnils(String doctorSnils) {
		if (doctorSnils != null)
			this.doctorSnils = doctorSnils.trim();
	}

	public void setLastName(String lastName) {
		if (lastName != null)
			this.lastName = lastName.trim();
	}

	public void setFirstName(String firstName) {
		if (firstName != null)
			this.firstName = firstName.trim();
	}

	public void setPatronymic(String patronymic) {
		if (patronymic != null)
			this.patronymic = patronymic.trim();
	}

	public void setBirthDay(String birthDay) {
		if (birthDay != null)
			this.birthDay = birthDay.trim();
	}

	public void setPolicyNum(String policyNum) {
		if (policyNum != null)
			this.policyNum = policyNum.trim();
	}

	public String getEffDate() {
		return effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setEffDate(String effDate) {
		if (effDate != null)
			this.effDate = effDate.trim();
	}

	public void setExpDate(String expDate) {
		if (expDate != null)
			this.expDate = expDate.trim();
	}

	public Boolean getHistorical() {
		return historical;
	}

	public void setHistorical(Boolean historical) {
		this.historical = historical;
	}

}
