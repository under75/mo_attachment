package ru.sartfoms.moattach.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "moattach_roles", schema = "BIGADMIN")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "u_name")
	private String userName;
	
	@Column(name = "role_name")
	private String roleName;
	
	@Column(name = "u_type")
	private String userType;

	public String getUserName() {
		return userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}
