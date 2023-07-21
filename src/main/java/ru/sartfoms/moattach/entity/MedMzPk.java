package ru.sartfoms.moattach.entity;

import java.io.Serializable;

public class MedMzPk implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer lpuId;

	private String snils;

	public Integer getLpuId() {
		return lpuId;
	}

	public String getSnils() {
		return snils;
	}

	public void setLpuId(Integer lpuId) {
		this.lpuId = lpuId;
	}

	public void setSnils(String snils) {
		this.snils = snils;
	}
	
	
}
