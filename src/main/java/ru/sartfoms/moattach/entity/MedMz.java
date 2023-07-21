package ru.sartfoms.moattach.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "MED_MZ", schema = "OMCOWNER")
@IdClass(MedMzPk.class)
public class MedMz {
	@Column(name = "im")
	private String firstName;

	@Column(name = "fam")
	private String lastName;

	@Column(name = "ot")
	private String patronymic;
	@Id
	@Column(name = "CD_LPU")
	private Integer lpuId;
	@Id
	@Column(name = "snils")
	private String snils;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public Integer getLpuId() {
		return lpuId;
	}

	public String getSnils() {
		return snils;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public void setLpuId(Integer lpuId) {
		this.lpuId = lpuId;
	}

	public void setSnils(String snils) {
		this.snils = snils;
	}
}
