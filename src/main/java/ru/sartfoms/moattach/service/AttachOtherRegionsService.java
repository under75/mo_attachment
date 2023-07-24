package ru.sartfoms.moattach.service;

import java.time.LocalDate;

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

	public void attach(User user, AttachFormParameters formParams, Gar gar, Person person, Policy policy) {
		AttachOtherRegions attachOtherRegions = new AttachOtherRegions();
		attachOtherRegions.setUsr(user.getName());
		attachOtherRegions.setEffDate(LocalDate.now());
		attachOtherRegions.setExpDate(LocalDate.parse(formParams.getAttachExpDate()));
		attachOtherRegions.setLastName(person.getLastName());
		attachOtherRegions.setFirstName(person.getFirstName());
		attachOtherRegions.setPatronymic(person.getPatronymic());
		attachOtherRegions.setBirthDay(person.getBirthDay());
		attachOtherRegions.setPcyOkato(policy.getOkato() != null ? policy.getOkato().getCode() : null);
		attachOtherRegions.setPcyType(policy.getPcyType());
		attachOtherRegions.setPcySer(policy.getPcySer());
		attachOtherRegions.setPcyNum(policy.getPcyNum());
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
		attachOtherRegions.setAoguidpr(gar.getLev4Pr().stream()
				.filter(t -> t.getId().longValue() == gar.getIdlev4Pr().longValue() && t.getIsActual()).findFirst()
				.get().getObjectguid());
		attachOtherRegions.setHsguidpr(gar.getLev5Pr().stream()
				.filter(t -> t.getId().longValue() == gar.getIdlev5Pr().longValue() && t.getIsActual()).findFirst()
				.get().getObjectguid());
		attachOtherRegions.setLpuId(formParams.getLpuId());
		attachOtherRegions.setLpuUnit(formParams.getLpuUnit());
		attachOtherRegions.setDoctorsnils(formParams.getDoctorSnils());
		
		attachOtherRegionsRepository.save(attachOtherRegions);
	}
}
