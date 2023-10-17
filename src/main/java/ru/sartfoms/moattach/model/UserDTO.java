package ru.sartfoms.moattach.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private LocalDateTime effDate;

	private String passwd;

	private Integer lpuId;

	private Collection<String> roles;

	private String lastname;

	private String firstname;

	private String patronymic;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Integer getLpuId() {
		return lpuId;
	}

	public Collection<String> getRoles() {
		return roles;
	}

	public void setLpuId(Integer lpuId) {
		this.lpuId = lpuId;
	}

	public void setRoles(Collection<String> roles) {
		this.roles = roles;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public LocalDateTime getEffDate() {
		return effDate;
	}

	public void setEffDate(LocalDateTime effDate) {
		this.effDate = effDate;
	}
	
}
