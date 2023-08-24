package ru.sartfoms.moattach.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.thymeleaf.util.StringUtils;

@Entity
@Table(name = "ATTACH_OTHERREGIONS", schema = "LPUOWNER")
public class AttachOtherRegions {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
	@SequenceGenerator(name = "id_sequence", sequenceName = "LPUOWNER.ATTACH_OTHERREGIONS_SEQ", allocationSize = 1)
	private Long id;
	@Column(name = "usr")
	private String usr;
	@Column(name = "effdate")
	private LocalDate effDate;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "lastname")
	private String lastName;
	@Column(name = "firstname")
	private String firstName;
	@Column(name = "patronymic")
	private String patronymic;
	@Column(name = "birthday")
	private LocalDate birthDay;
	@Column(name = "pcyokato")
	private String pcyOkato;
	@Column(name = "pcytype")
	private String pcyType;
	@Column(name = "pcyser")
	private String pcySer;
	@Column(name = "pcynum")
	private String pcyNum;
	@Column(name = "dudltype")
	private String dudlType;
	@Column(name = "dudlser")
	private String dudlSer;
	@Column(name = "dudlnum")
	private String dudlNum;
	@Column(name = "predstdoc")
	private String predstDoc;
	@Column(name = "phone")
	private String phone;
	@Column(name = "email")
	private String email;
	@Column(name = "aoguidreg")
	private String aoguidreg;
	@Column(name = "hsguidreg")
	private String hsguidreg;
	@Column(name = "aoguidpr")
	private String aoguidpr;
	@Column(name = "hsguidpr")
	private String hsguidpr;
	@Column(name = "lpuid")
	private Integer lpuId;
	@Column(name = "lpuunit")
	private String lpuUnit;
	@Column(name = "doctorsnils")
	private String doctorsnils;
	@Column(name = "period")
	private Integer period;
	@Column(name = "dtins")
	private LocalDateTime dtIns;
	@Column(name="gender")
	private Integer gender;
	@Column(name="contract")
	private LocalDate contract;
	
	public Long getId() {
		return id;
	}
	public String getUsr() {
		return usr;
	}
	public LocalDate getEffDate() {
		return effDate;
	}
	public LocalDate getExpDate() {
		return expDate;
	}
	public String getLastName() {
		return StringUtils.capitalize(lastName != null ? lastName.toLowerCase() : "");
	}
	public String getFirstName() {
		return StringUtils.capitalize(firstName != null ? firstName.toLowerCase() : "");
	}
	public String getPatronymic() {
		return StringUtils.capitalize(patronymic != null ? patronymic.toLowerCase() : "");
	}
	public LocalDate getBirthDay() {
		return birthDay;
	}
	public String getPcyOkato() {
		return pcyOkato;
	}
	public String getPcyType() {
		return pcyType;
	}
	public String getPcySer() {
		return pcySer;
	}
	public String getPcyNum() {
		return pcyNum;
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
	public String getPredstDoc() {
		return predstDoc;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public String getAoguidreg() {
		return aoguidreg;
	}
	public String getHsguidreg() {
		return hsguidreg;
	}
	public String getAoguidpr() {
		return aoguidpr;
	}
	public String getHsguidpr() {
		return hsguidpr;
	}
	public Integer getLpuId() {
		return lpuId;
	}
	public String getLpuUnit() {
		return lpuUnit;
	}
	public String getDoctorsnils() {
		return doctorsnils;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUsr(String usr) {
		this.usr = usr;
	}
	public void setEffDate(LocalDate effDate) {
		this.effDate = effDate;
	}
	public void setExpDate(LocalDate expDate) {
		this.expDate = expDate;
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
	public void setPcyOkato(String pcyOkato) {
		this.pcyOkato = pcyOkato;
	}
	public void setPcyType(String pcyType) {
		this.pcyType = pcyType;
	}
	public void setPcySer(String pcySer) {
		this.pcySer = pcySer;
	}
	public void setPcyNum(String pcyNum) {
		this.pcyNum = pcyNum;
	}
	public void setDudlType(String dudlType) {
		this.dudlType = dudlType;
	}
	public void setDudlSer(String dudlSer) {
		if (dudlSer.matches("^\\d{4}$")) {
			dudlSer = dudlSer.substring(0, 2) + " " + dudlSer.substring(2);
		}
		this.dudlSer = dudlSer;
	}
	public void setDudlNum(String dudlNum) {
		this.dudlNum = dudlNum;
	}
	public void setPredstDoc(String predstDoc) {
		this.predstDoc = predstDoc;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAoguidreg(String aoguidreg) {
		this.aoguidreg = aoguidreg;
	}
	public void setHsguidreg(String hsguidreg) {
		this.hsguidreg = hsguidreg;
	}
	public void setAoguidpr(String aoguidpr) {
		this.aoguidpr = aoguidpr;
	}
	public void setHsguidpr(String hsguidpr) {
		this.hsguidpr = hsguidpr;
	}
	public void setLpuId(Integer lpuId) {
		this.lpuId = lpuId;
	}
	public void setLpuUnit(String lpuUnit) {
		this.lpuUnit = lpuUnit;
	}
	public void setDoctorsnils(String doctorsnils) {
		this.doctorsnils = doctorsnils;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public LocalDateTime getDtIns() {
		return dtIns;
	}
	public void setDtIns(LocalDateTime dtIns) {
		this.dtIns = dtIns;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public LocalDate getContract() {
		return contract;
	}
	public void setContract(LocalDate contract) {
		this.contract = contract;
	}
}
