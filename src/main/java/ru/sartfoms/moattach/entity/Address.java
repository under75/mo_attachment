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
@Table(name = "mpi_address", schema = "ASY23")
@IdClass(CompositeKey.class)
public class Address {
	@Id
	@Column(name = "rid")
	private Long rid;

	@Id
	@Column(name = "nr")
	private Integer nr;

	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE) 
	@JoinColumn(name = "okato", referencedColumnName = "cod")
	private Okato okato;

	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE) 
	@JoinColumn(name = "oksm", referencedColumnName = "alfa3")
	private Oksm oksm;

	@Column(name = "aoguid")
	private String aoguid;

	@Column(name = "hsguid")
	private String hsguid;

	@Column(name = "addresstext")
	private String addressText;

	@Column(name = "mailindex")
	private String mailIndex;

	@Column(name = "appnum")
	private String appNum;

	@Column(name = "dsourcetype")
	private String dsourceType;

	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE) 
	@JoinColumn(name = "dsource", referencedColumnName = "cod", insertable=false, updatable=false)
	private Okato dsource;
	
	@Column(name = "dsource")
	private String dsourceStr;

	@Column(name = "addresstype")
	private String addressType;

	@Column(name = "addressdateb")
	private LocalDate addressDateB;

	@Column(name = "addressdatee")
	private LocalDate addressDateE;
	
	@Column(name = "addressstatus")
	private String status;

	public Address() {
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

	public Okato getOkato() {
		return okato;
	}

	public void setOkato(Okato okato) {
		this.okato = okato;
	}

	public Oksm getOksm() {
		return oksm;
	}

	public void setOksm(Oksm oksm) {
		this.oksm = oksm;
	}

	public String getAoguid() {
		return aoguid;
	}

	public void setAoguid(String aoguid) {
		this.aoguid = aoguid;
	}

	public String getHsguid() {
		return hsguid;
	}

	public void setHsguid(String hsguid) {
		this.hsguid = hsguid;
	}

	public String getAddressText() {
		return addressText;
	}

	public void setAddressText(String addressText) {
		this.addressText = addressText;
	}

	public String getMailIndex() {
		return mailIndex;
	}

	public void setMailIndex(String mailIndex) {
		this.mailIndex = mailIndex;
	}

	public String getAppNum() {
		return appNum;
	}

	public void setAppNum(String appNum) {
		this.appNum = appNum;
	}

	public String getDsourceType() {
		return dsourceType;
	}

	public void setDsourceType(String dsourceType) {
		this.dsourceType = dsourceType;
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

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public LocalDate getAddressDateB() {
		return addressDateB;
	}

	public LocalDate getAddressDateE() {
		return addressDateE;
	}

	public void setAddressDateB(LocalDate addressDateB) {
		this.addressDateB = addressDateB;
	}

	public void setAddressDateE(LocalDate addressDateE) {
		this.addressDateE = addressDateE;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
