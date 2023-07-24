package ru.sartfoms.moattach.entity;

import java.time.LocalDate;
import java.util.Date;

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
@Table(name = "mpi_dudl", schema = "ASY23")
@IdClass(CompositeKey.class)
public class Dudl {
	@Id
	@Column(name = "rid")
	private Long rid;

	@Id
	@Column(name = "nr")
	private Integer nr;

	@Column(name = "dudlser")
	private String dudlSer;

	@Column(name = "dudlnum")
	private String dudlNum;

	@Column(name = "dudldateb")
	private LocalDate dudlDateB;

	@Column(name = "dudldatee")
	private LocalDate dudlDateE;

	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "dudltype", referencedColumnName = "code",insertable=false, updatable=false)
	private DudlType dudlType;
	
	@Column(name = "dudltype")
	private String dudlTypeStr;

	@Column(name = "issuer")
	private String issuer;

	@OneToOne
	@JoinColumn(name = "issueroksm", referencedColumnName = "alfa3")
	private Oksm issuerOksm;

	@OneToOne
	@JoinColumn(name = "ctznoksm", referencedColumnName = "alfa3")
	private Oksm ctznOksm;

	@Column(name = "nocitizenship")
	private Boolean noCitizenship;

	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE) 
	@JoinColumn(name = "dsource", referencedColumnName = "cod", insertable=false, updatable=false)
	private Okato dsource;
	
	@Column(name = "dsource")
	private String dsourceStr;

	@Column(name = "descr")
	private String descr;

	@Column(name = "fam")
	private String lastName;

	@Column(name = "ot")
	private String patronymic;

	@Column(name = "im")
	private String firstName;

	@Column(name = "dr")
	private Date birthDay;

	@Column(name = "birthplace")
	private String birthPlace;

	@Column(name = "sex")
	private Integer gender;

	@Column(name = "dsourcetype")
	private String dsourceType;

	@OneToOne
	@JoinColumn(name = "birthoksm", referencedColumnName = "alfa3")
	private Oksm birthOksm;

	@Column(name = "dudlstatus")
	private String dudlStatus;

	@Column(name = "dost")
	private String dost;

	public Dudl() {
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

	public String getDudlSer() {
		return dudlSer;
	}

	public void setDudlSer(String dudlSer) {
		this.dudlSer = dudlSer;
	}

	public String getDudlNum() {
		return dudlNum;
	}

	public void setDudlNum(String dudlNum) {
		this.dudlNum = dudlNum;
	}

	public LocalDate getDudlDateB() {
		return dudlDateB;
	}

	public LocalDate getDudlDateE() {
		return dudlDateE;
	}

	public void setDudlDateB(LocalDate dudlDateB) {
		this.dudlDateB = dudlDateB;
	}

	public void setDudlDateE(LocalDate dudlDateE) {
		this.dudlDateE = dudlDateE;
	}

	public DudlType getDudlType() {
		return dudlType;
	}

	public void setDudlType(DudlType dudlType) {
		this.dudlType = dudlType;
	}

	public String getDudlTypeStr() {
		return dudlTypeStr;
	}

	public void setDudlTypeStr(String dudlTypeStr) {
		this.dudlTypeStr = dudlTypeStr;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public Oksm getIssuerOksm() {
		return issuerOksm;
	}

	public void setIssuerOksm(Oksm issuerOksm) {
		this.issuerOksm = issuerOksm;
	}

	public Oksm getCtznOksm() {
		return ctznOksm;
	}

	public void setCtznOksm(Oksm ctznOksm) {
		this.ctznOksm = ctznOksm;
	}

	public Boolean getNoCitizenship() {
		return noCitizenship;
	}

	public void setNoCitizenship(Boolean noCitizenship) {
		this.noCitizenship = noCitizenship;
	}

	public Okato getDsource() {
		return dsource;
	}

	public void setDsource(Okato dsource) {
		this.dsource = dsource;
	}

	public String getDsourceStr() {
		return dsourceStr;
	}

	public void setDsourceStr(String dsourceStr) {
		this.dsourceStr = dsourceStr;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getDsourceType() {
		return dsourceType;
	}

	public void setDsourceType(String dsourceType) {
		this.dsourceType = dsourceType;
	}

	public Oksm getBirthOksm() {
		return birthOksm;
	}

	public void setBirthOksm(Oksm birthOksm) {
		this.birthOksm = birthOksm;
	}

	public String getDudlStatus() {
		return dudlStatus;
	}

	public void setDudlStatus(String dudlStatus) {
		this.dudlStatus = dudlStatus;
	}

	public String getDost() {
		return dost;
	}

	public void setDost(String dost) {
		this.dost = dost;
	}

}
