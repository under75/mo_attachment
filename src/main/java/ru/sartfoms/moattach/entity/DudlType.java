package ru.sartfoms.moattach.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_spr_dok", schema = "APPLGAR")
public class DudlType implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "code")
	private String docCode;
	
	@Column(name = "title")
	private String docName;
	
	public DudlType() {
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

}
