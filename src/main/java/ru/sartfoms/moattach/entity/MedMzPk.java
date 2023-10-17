package ru.sartfoms.moattach.entity;

import java.io.Serializable;
import java.util.Objects;

public class MedMzPk implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer lpuId;

	private String snils;
	
	public MedMzPk() {
	}

	public MedMzPk(Integer id, String doctorSnils) {
		lpuId = id;
		snils = doctorSnils;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(lpuId, snils);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedMzPk other = (MedMzPk) obj;
		return Objects.equals(lpuId, other.lpuId) && Objects.equals(snils, other.snils);
	}
	
	
}
