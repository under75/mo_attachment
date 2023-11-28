package ru.sartfoms.moattach.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_ATTACH_OTHERREGIONS_INSURED", schema = "LPUOWNER")
public class VAttachOtherRegionsInsured {
	@Id
	private Long id;
	@Column(name = "lastname")
	private String lastName;
	@Column(name = "firstname")
	private String firstName;
	@Column(name = "patronymic")
	private String patronymic;
	@Column(name = "birthday")
	private LocalDate birthDay;
	@Column(name = "enp_attach")
	private String enpAttach;
	@Column(name = "pcyokato")
	private String pcyOkato;
	@Column(name = "attachdate")
	private LocalDate attachDate;
	@Column(name = "enp_insur")
	private String enpInsur;
	@Column(name = "smo_id")
	private Integer smoId;
	@Column(name = "fsmo_id")
	private Integer fsmoId;
	@Column(name = "insurdate")
	private LocalDate insurDate;
	@Column(name = "lpu_id")
	private Integer lpuId;
	@Column(name = "expdate")
	private LocalDate expDate;
	
	public String getLastName() {
		return lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getPatronymic() {
		return patronymic;
	}
	public LocalDate getBirthDay() {
		return birthDay;
	}
	public String getEnpAttach() {
		return enpAttach;
	}
	public String getPcyOkato() {
		return pcyOkato;
	}
	public LocalDate getAttachDate() {
		return attachDate;
	}
	public String getEnpInsur() {
		return enpInsur;
	}
	public Integer getSmoId() {
		return smoId;
	}
	public Integer getFsmoId() {
		return fsmoId;
	}
	public LocalDate getInsurDate() {
		return insurDate;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}
	public void setEnpAttach(String enpAttach) {
		this.enpAttach = enpAttach;
	}
	public void setPcyOkato(String pcyOkato) {
		this.pcyOkato = pcyOkato;
	}
	public void setAttachDate(LocalDate attachDate) {
		this.attachDate = attachDate;
	}
	public void setEnpInsur(String enpInsur) {
		this.enpInsur = enpInsur;
	}
	public void setSmoId(Integer smoId) {
		this.smoId = smoId;
	}
	public void setFsmoId(Integer fsmoId) {
		this.fsmoId = fsmoId;
	}
	public void setInsurDate(LocalDate insurDate) {
		this.insurDate = insurDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getLpuId() {
		return lpuId;
	}
	public void setLpuId(Integer lpuId) {
		this.lpuId = lpuId;
	}
	public LocalDate getExpDate() {
		return expDate;
	}
	public void setExpDate(LocalDate expDate) {
		this.expDate = expDate;
	}
	
}
