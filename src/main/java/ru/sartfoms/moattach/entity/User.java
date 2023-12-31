package ru.sartfoms.moattach.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "moattach_users", schema = "BIGADMIN")
public class User {

	@Id
	@Column(name = "u_name")
	private String name;

	@Column(name = "u_type")
	private String type;

	@Column(name = "u_lpu")
	private Integer lpuId;
	
	@Column(name = "u_fam")
	private String lastname;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "u_name", referencedColumnName = "u_name")
	private Collection<Role> roles = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLpuId() {
		return lpuId;
	}

	public void setLpuId(Integer lpuId) {
		this.lpuId = lpuId;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
