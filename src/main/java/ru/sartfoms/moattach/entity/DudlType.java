package ru.sartfoms.moattach.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "spr_dok", schema = "OMCOWNER")
public class DudlType {
	
	@Id
	@Column(name = "cd_dok")
	private Integer docCode;
	
	@Column(name = "nm_dok")
	private String docName;
	
	public DudlType() {
	}

	public Integer getDocCode() {
		return docCode;
	}

	public void setDocCode(Integer docCode) {
		this.docCode = docCode;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

}
