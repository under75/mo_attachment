package ru.sartfoms.moattach.model;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import ru.sartfoms.moattach.entity.AsAddrObj;
import ru.sartfoms.moattach.entity.House;

public class Gar {
	private Collection<AsAddrObj> lev1Rg;
	private Collection<AsAddrObj> lev2Rg;
	private Collection<AsAddrObj> lev3Rg;
	private Collection<AsAddrObj> lev4Rg;
	private Collection<House> lev5Rg;
	private Collection<AsAddrObj> lev1Pr;
	private Collection<AsAddrObj> lev2Pr;
	private Collection<AsAddrObj> lev3Pr;
	private Collection<AsAddrObj> lev4Pr;
	private Collection<House> lev5Pr;
	private Long idlev1Rg;
	private Long idlev2Rg;
	private Long idlev3Rg;
	private Long idlev4Rg;
	private Long idlev5Rg;
	@NotNull
	private Long idlev1Pr;
	@NotNull
	private Long idlev2Pr;
	@NotNull
	private Long idlev3Pr;
	@NotNull
	private Long idlev4Pr;
	@NotNull
	private Long idlev5Pr;
	private String flev1Rg = "";
	private String flev2Rg = "";
	private String flev3Rg = "";
	private String flev4Rg = "";
	private String flev5Rg = "";
	private String flev1Pr = "";
	private String flev2Pr = "";
	private String flev3Pr = "";
	private String flev4Pr = "";
	private String flev5Pr = "";

	public Collection<AsAddrObj> getLev1Rg() {
		return lev1Rg;
	}

	public Collection<AsAddrObj> getLev2Rg() {
		return lev2Rg;
	}

	public Collection<AsAddrObj> getLev3Rg() {
		return lev3Rg;
	}

	public Collection<AsAddrObj> getLev4Rg() {
		return lev4Rg;
	}

	public Collection<House> getLev5Rg() {
		return lev5Rg;
	}

	public Collection<AsAddrObj> getLev1Pr() {
		return lev1Pr;
	}

	public Collection<AsAddrObj> getLev2Pr() {
		return lev2Pr;
	}

	public Collection<AsAddrObj> getLev3Pr() {
		return lev3Pr;
	}

	public Collection<AsAddrObj> getLev4Pr() {
		return lev4Pr;
	}

	public Collection<House> getLev5Pr() {
		return lev5Pr;
	}

	public Long getIdlev1Rg() {
		return idlev1Rg;
	}

	public Long getIdlev2Rg() {
		return idlev2Rg;
	}

	public Long getIdlev3Rg() {
		return idlev3Rg;
	}

	public Long getIdlev4Rg() {
		return idlev4Rg;
	}

	public Long getIdlev1Pr() {
		return idlev1Pr;
	}

	public Long getIdlev2Pr() {
		return idlev2Pr;
	}

	public Long getIdlev3Pr() {
		return idlev3Pr;
	}

	public Long getIdlev4Pr() {
		return idlev4Pr;
	}

	public void setLev1Rg(Collection<AsAddrObj> lev1Rg) {
		this.lev1Rg = lev1Rg;
	}

	public void setLev2Rg(Collection<AsAddrObj> lev2Rg) {
		this.lev2Rg = lev2Rg;
	}

	public void setLev3Rg(Collection<AsAddrObj> lev3Rg) {
		this.lev3Rg = lev3Rg;
	}

	public void setLev4Rg(Collection<AsAddrObj> lev4Rg) {
		this.lev4Rg = lev4Rg;
	}

	public void setLev5Rg(Collection<House> lev5Rg) {
		this.lev5Rg = lev5Rg;
	}

	public void setLev1Pr(Collection<AsAddrObj> lev1Pr) {
		this.lev1Pr = lev1Pr;
	}

	public void setLev2Pr(Collection<AsAddrObj> lev2Pr) {
		this.lev2Pr = lev2Pr;
	}

	public void setLev3Pr(Collection<AsAddrObj> lev3Pr) {
		this.lev3Pr = lev3Pr;
	}

	public void setLev4Pr(Collection<AsAddrObj> lev4Pr) {
		this.lev4Pr = lev4Pr;
	}

	public void setLev5Pr(Collection<House> lev5Pr) {
		this.lev5Pr = lev5Pr;
	}

	public void setIdlev1Rg(Long idlev1Rg) {
		this.idlev1Rg = idlev1Rg;
	}

	public void setIdlev2Rg(Long idlev2Rg) {
		this.idlev2Rg = idlev2Rg;
	}

	public void setIdlev3Rg(Long idlev3Rg) {
		this.idlev3Rg = idlev3Rg;
	}

	public void setIdlev4Rg(Long idlev4Rg) {
		this.idlev4Rg = idlev4Rg;
	}

	public void setIdlev1Pr(Long idlev1Pr) {
		this.idlev1Pr = idlev1Pr;
	}

	public void setIdlev2Pr(Long idlev2Pr) {
		this.idlev2Pr = idlev2Pr;
	}

	public void setIdlev3Pr(Long idlev3Pr) {
		this.idlev3Pr = idlev3Pr;
	}

	public void setIdlev4Pr(Long idlev4Pr) {
		this.idlev4Pr = idlev4Pr;
	}

	public String getFlev1Rg() {
		return flev1Rg;
	}

	public String getFlev2Rg() {
		return flev2Rg;
	}

	public String getFlev3Rg() {
		return flev3Rg;
	}

	public String getFlev4Rg() {
		return flev4Rg;
	}

	public String getFlev5Rg() {
		return flev5Rg;
	}

	public String getFlev1Pr() {
		return flev1Pr;
	}

	public String getFlev2Pr() {
		return flev2Pr;
	}

	public String getFlev3Pr() {
		return flev3Pr;
	}

	public String getFlev4Pr() {
		return flev4Pr;
	}

	public String getFlev5Pr() {
		return flev5Pr;
	}

	public void setFlev1Rg(String flev1Rg) {
		this.flev1Rg = flev1Rg.trim();
	}

	public void setFlev2Rg(String flev2Rg) {
		this.flev2Rg = flev2Rg.trim();
	}

	public void setFlev3Rg(String flev3Rg) {
		this.flev3Rg = flev3Rg.trim();
	}

	public void setFlev4Rg(String flev4Rg) {
		this.flev4Rg = flev4Rg.trim();
	}

	public void setFlev5Rg(String flev5Rg) {
		this.flev5Rg = flev5Rg.trim();
	}

	public void setFlev1Pr(String flev1Pr) {
		this.flev1Pr = flev1Pr.trim();
	}

	public void setFlev2Pr(String flev2Pr) {
		this.flev2Pr = flev2Pr.trim();
	}

	public void setFlev3Pr(String flev3Pr) {
		this.flev3Pr = flev3Pr.trim();
	}

	public void setFlev4Pr(String flev4Pr) {
		this.flev4Pr = flev4Pr.trim();
	}

	public void setFlev5Pr(String flev5Pr) {
		this.flev5Pr = flev5Pr.trim();
	}

	public Long getIdlev5Rg() {
		return idlev5Rg;
	}

	public Long getIdlev5Pr() {
		return idlev5Pr;
	}

	public void setIdlev5Rg(Long idlev5Rg) {
		this.idlev5Rg = idlev5Rg;
	}

	public void setIdlev5Pr(Long idlev5Pr) {
		this.idlev5Pr = idlev5Pr;
	}
}
