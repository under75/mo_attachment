package ru.sartfoms.moattach.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.AttachOtherRegions;
import ru.sartfoms.moattach.entity.Person;
import ru.sartfoms.moattach.entity.Policy;
import ru.sartfoms.moattach.entity.User;
import ru.sartfoms.moattach.model.AttachFormParameters;
import ru.sartfoms.moattach.model.Gar;
import ru.sartfoms.moattach.repository.AttachOtherRegionsRepository;

@Service
public class AttachOtherRegionsService {
	private final AttachOtherRegionsRepository attachOtherRegionsRepository;

	public AttachOtherRegionsService(AttachOtherRegionsRepository attachOtherRegionsRepository) {
		this.attachOtherRegionsRepository = attachOtherRegionsRepository;
	}

	public AttachOtherRegions attach(User user, AttachFormParameters formParams, Gar gar, Person person,
			Policy policy) {
		AttachOtherRegions attachOtherRegions = new AttachOtherRegions();
		attachOtherRegions.setUsr(user.getName());
		attachOtherRegions.setDtIns(LocalDateTime.now());
		attachOtherRegions.setEffDate(LocalDate.parse(formParams.getEffDate()));
		attachOtherRegions.setPeriod(formParams.getPeriod());
		attachOtherRegions.setLastName(person.getLastName());
		attachOtherRegions.setFirstName(person.getFirstName());
		attachOtherRegions.setPatronymic(person.getPatronymic());
		attachOtherRegions.setBirthDay(person.getBirthDay());
		attachOtherRegions.setPcyOkato(policy.getOkato() != null ? policy.getOkato().getCode() : null);
		attachOtherRegions.setPcyType(policy.getPcyType());
		attachOtherRegions.setPcySer(policy.getPcySer());
		attachOtherRegions.setPcyNum(policy.getEnp() != null ? policy.getEnp() : policy.getPcyNum());
		attachOtherRegions.setDudlType(String.valueOf(formParams.getDudlType()));
		attachOtherRegions.setDudlSer(formParams.getDudlSer());
		attachOtherRegions.setDudlNum(formParams.getDudlNum());
		attachOtherRegions.setPredstDoc(formParams.getDudlPredst());
		attachOtherRegions.setPhone(formParams.getPhone());
		attachOtherRegions.setEmail(formParams.getEmail());
		if (gar.getIdlev4Rg() != null)
			attachOtherRegions.setAoguidreg(gar.getLev4Rg().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev4Rg().longValue() && t.getIsActual()).findFirst()
					.get().getObjectguid());
		if (gar.getIdlev5Rg() != null)
			attachOtherRegions.setHsguidreg(gar.getLev5Rg().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev5Rg().longValue() && t.getIsActual()).findFirst()
					.get().getObjectguid());
		if (gar.getLev4Pr() != null)
			attachOtherRegions.setAoguidpr(gar.getLev4Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev4Pr().longValue() && t.getIsActual()).findFirst()
					.get().getObjectguid());
		else
			attachOtherRegions.setAoguidpr(gar.getLev3Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev3Pr().longValue() && t.getIsActual()).findFirst()
					.get().getObjectguid());
		attachOtherRegions.setHsguidpr(gar.getLev5Pr().stream()
				.filter(t -> t.getId().longValue() == gar.getIdlev5Pr().longValue() && t.getIsActual()).findFirst()
				.get().getObjectguid());
		attachOtherRegions.setLpuId(formParams.getLpuId());
		attachOtherRegions.setLpuUnit(formParams.getLpuUnit());
		attachOtherRegions.setDoctorsnils(formParams.getDoctorSnils());

		return attachOtherRegionsRepository.save(attachOtherRegions);
	}

	/**
	 * public String getAddrPrStr(Gar gar) { return gar.getLev1Pr().stream()
	 * .filter(t -> t.getId().longValue() == gar.getIdlev1Pr().longValue() &&
	 * t.getIsActual()) .map(t -> t.getName() + " " +
	 * t.getTypename()).findFirst().get().trim() + ", " + gar.getLev2Pr().stream()
	 * .filter(t -> t.getId().longValue() == gar.getIdlev2Pr().longValue() &&
	 * t.getIsActual()) .map(t -> t.getName() + " " +
	 * t.getTypename()).findFirst().get().trim() + ", " + gar.getLev3Pr().stream()
	 * .filter(t -> t.getId().longValue() == gar.getIdlev3Pr().longValue() &&
	 * t.getIsActual()) .map(t -> t.getName() + " " +
	 * t.getTypename()).findFirst().get().trim() + (gar.getIdlev4Pr() != null ? ", "
	 * + gar.getLev4Pr().stream() .filter(t -> t.getId().longValue() ==
	 * gar.getIdlev4Pr() && t.getIsActual()) .map(t -> t.getName() + " " +
	 * t.getTypename()).findFirst().get().trim() : "") + ", " +
	 * gar.getLev5Pr().stream() .filter(t -> t.getId().longValue() ==
	 * gar.getIdlev5Pr().longValue() && t.getIsActual()) .map(t -> t.getHouseNum() +
	 * " " + (t.getAddNum1() != null ? t.getAddNum1() : "") + " " + (t.getAddNum2()
	 * != null ? t.getAddNum2() : "")) .findFirst().get().trim(); }
	 **/
	public AttachOtherRegions findByPcyNum(String pcyNum) {
		return attachOtherRegionsRepository.findByPcyNum(pcyNum, LocalDate.now());
	}

	public boolean isAttachEffective(AttachOtherRegions attachOtherRegions) {
		LocalDate now = LocalDate.now();
		return (attachOtherRegions.getExpDate() != null && attachOtherRegions.getExpDate().isAfter(now)
				&& attachOtherRegions.getEffDate().plusDays(attachOtherRegions.getPeriod()).isAfter(now))
				|| (attachOtherRegions.getExpDate() == null
						&& attachOtherRegions.getEffDate().plusDays(attachOtherRegions.getPeriod()).isAfter(now));
	}

	public AttachOtherRegions getReferenceById(Optional<Long> attachId) {
		return attachOtherRegionsRepository.getReferenceById(attachId.get());
	}

	public void save(AttachOtherRegions attachOtherRegions) {
		attachOtherRegionsRepository.save(attachOtherRegions);
	}
}
