package ru.sartfoms.moattach.model;

import javax.validation.constraints.Size;

public class FerzlSearchParameters {
	private String policyType;
	@Size(max = 10)
	private String policySer;
	@Size(max = 16)
	private String policyNum;
	private Integer dudlType;
	@Size(max = 12)
	private String dudlSer;
	@Size(max = 20)
	private String dudlNum;
	@Size(max = 40)
	private String lastName;
	@Size(max = 40)
	private String firstName;
	@Size(max = 40)
	private String patronymic;
	private String birthDay;
	private String dateFrom;
	private String dateTo;

	public String getPolicyType() {
		return policyType;
	}

	public String getPolicySer() {
		return policySer;
	}

	public String getPolicyNum() {
		return policyNum;
	}

	public Integer getDudlType() {
		return dudlType;
	}

	public String getDudlSer() {
		return dudlSer;
	}

	public String getDudlNum() {
		return dudlNum;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public void setPolicySer(String policySer) {
		this.policySer = policySer.trim();
	}

	public void setPolicyNum(String policyNum) {
		this.policyNum = policyNum.trim();
	}

	public void setDudlType(Integer dudlType) {
		this.dudlType = dudlType;
	}

	public void setDudlSer(String dudlSer) {
		this.dudlSer = dudlSer.trim();
	}

	public void setDudlNum(String dudlNum) {
		this.dudlNum = dudlNum.trim();
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
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

	public void setLastName(String lastName) {
		this.lastName = lastName.trim();
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName.trim();
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic.trim();
	}
}
