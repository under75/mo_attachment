package ru.sartfoms.moattach.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "mpi_attach", schema = "OMCOWNER")
@IdClass(CompositeKey.class)
public class Attach {
	@Id
	@Column(name = "rid")
	private Long rid;

	@Id
	@Column(name = "nr")
	private Integer nr;
	
	@Column(name = "areatype")
	private Integer areaType;
	
	@Column(name = "areaid")
	private String areaId;
	
	@Column(name = "attachmethod")
	private Integer attachMethod;
	
	@Column(name = "mcapitation")
	private Integer mCapitation;
	
	@Column(name = "dateattachb")
	private LocalDate dateAttachB;
	
	@Column(name = "dateattache")
	private LocalDate dateAttachE;
	
	@Column(name = "moid")
	private String moId;
	
	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE) 
	@JoinColumn(name = "mocode", referencedColumnName = "mcod", insertable=false, updatable=false)
	private Lpu lpu;
	
	@Column(name = "mocode")
	private String mocode;
	
	@Column(name = "mofid")
	private String moFId;
	
	@Column(name = "snilsdoctor")
	private String snilsDoctor;
	
	@Column(name = "doctorid")
	private String doctorId;
	
	@Column(name = "doctorsince")
	private LocalDate doctorSince;
	
	@Column(name = "descr")
	private String descr;
	
	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE) 
	@JoinColumn(name = "mookato", referencedColumnName = "cod", insertable=false, updatable=false)
	private Okato moOkato;
	
	@Column(name = "attachstatus")
	private String attachStatus;
	
	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE) 
	@JoinColumn(name = "dsource", referencedColumnName = "cod", insertable=false, updatable=false)
	private Okato dsource;
	
	@Column(name = "dsource")
	private String dsourceStr;
	
	@Column(name = "dsourcetype")
	private String dsourceType;
	
	public Attach() {
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public Integer getAreaType() {
		return areaType;
	}

	public void setAreaType(Integer areaType) {
		this.areaType = areaType;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public Integer getAttachMethod() {
		return attachMethod;
	}

	public void setAttachMethod(Integer attachMethod) {
		this.attachMethod = attachMethod;
	}

	public Integer getmCapitation() {
		return mCapitation;
	}

	public void setmCapitation(Integer mCapitation) {
		this.mCapitation = mCapitation;
	}

	public LocalDate getDateAttachB() {
		return dateAttachB;
	}

	public LocalDate getDateAttachE() {
		return dateAttachE;
	}

	public void setDateAttachB(LocalDate dateAttachB) {
		this.dateAttachB = dateAttachB;
	}

	public void setDateAttachE(LocalDate dateAttachE) {
		this.dateAttachE = dateAttachE;
	}

	public String getMoId() {
		return moId;
	}

	public void setMoId(String moId) {
		this.moId = moId;
	}

	public Lpu getLpu() {
		return lpu;
	}

	public void setLpu(Lpu lpu) {
		this.lpu = lpu;
	}

	public String getMocode() {
		return mocode;
	}

	public void setMocode(String mocode) {
		this.mocode = mocode;
	}

	public void setDsource(Okato dsource) {
		this.dsource = dsource;
	}

	public String getMoFId() {
		return moFId;
	}

	public void setMoFId(String moFId) {
		this.moFId = moFId;
	}

	public String getSnilsDoctor() {
		return snilsDoctor;
	}

	public void setSnilsDoctor(String snilsDoctor) {
		this.snilsDoctor = snilsDoctor;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public LocalDate getDoctorSince() {
		return doctorSince;
	}

	public void setDoctorSince(LocalDate doctorSince) {
		this.doctorSince = doctorSince;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Okato getMoOkato() {
		return moOkato;
	}

	public void setMoOkato(Okato moOkato) {
		this.moOkato = moOkato;
	}

	public String getAttachStatus() {
		return attachStatus;
	}

	public void setAttachStatus(String attachStatus) {
		this.attachStatus = attachStatus;
	}

	public Okato getDsource() {
		return dsource;
	}

	public String getDsourceStr() {
		return dsourceStr;
	}

	public void setDsourceStr(String dsourceStr) {
		this.dsourceStr = dsourceStr;
	}

	public String getDsourceType() {
		return dsourceType;
	}

	public void setDsourceType(String dsourceType) {
		this.dsourceType = dsourceType;
	}
	
}
