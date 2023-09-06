package ru.sartfoms.moattach.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "МО_РОССИИ", schema = "LPUOWNER")
public class RussiaMo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "mcod")
	private Integer id;
	
	@Column(name = "nam_mop")
	private String fullName;
	
	@Column(name = "nam_mok")
	private String shortName;
	
	@Column(name = "addr_j")
	private String address;

	public Integer getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public String getAddress() {
		return address;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
