package ru.sartfoms.moattach.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import ru.sartfoms.moattach.dao.AttachOtherRegionsDao;
import ru.sartfoms.moattach.entity.AttachOtherRegions;
import ru.sartfoms.moattach.entity.Person;
import ru.sartfoms.moattach.entity.Policy;
import ru.sartfoms.moattach.entity.User;
import ru.sartfoms.moattach.model.AttachFormParameters;
import ru.sartfoms.moattach.model.Gar;
import ru.sartfoms.moattach.repository.AttachOtherRegionsRepository;
import ru.sartfoms.moattach.util.DateValidator;

@Service
public class AttachOtherRegionsService {
	private static final Integer PAGE_SIZE = 10;
	private final AttachOtherRegionsRepository attachOtherRegionsRepository;
	private final AttachOtherRegionsDao attachOtherRegionsDao;

	public AttachOtherRegionsService(AttachOtherRegionsRepository attachOtherRegionsRepository,
			AttachOtherRegionsDao attachOtherRegionsDao) {
		this.attachOtherRegionsRepository = attachOtherRegionsRepository;
		this.attachOtherRegionsDao = attachOtherRegionsDao;
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
		attachOtherRegions.setGender(person.getGender());

		return attachOtherRegionsRepository.save(attachOtherRegions);
	}

	public AttachOtherRegions findByPcyNum(String pcyNum) {
		return attachOtherRegionsRepository.findByPcyNum(pcyNum);
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

	public Boolean isUnpinned(LocalDate expDate) {
		LocalDate now = LocalDate.now();

		return expDate != null && (expDate.isBefore(now) || expDate.isEqual(now));
	}

	public void save(AttachOtherRegions attachOtherRegions, AttachFormParameters formParams, Gar gar, String usr) {
		attachOtherRegions.setPhone(formParams.getPhone());
		attachOtherRegions.setEmail(formParams.getEmail());
		if (gar.getLev4Pr() != null && gar.getIdlev4Pr() != null) {
			attachOtherRegions.setAoguidpr(gar.getLev4Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev4Pr().longValue() && t.getIsActual()).findAny()
					.get().getObjectguid());
		} else if (gar.getLev3Pr() != null && gar.getIdlev3Pr() != null) {
			attachOtherRegions.setAoguidpr(gar.getLev3Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev3Pr().longValue() && t.getIsActual()).findAny()
					.get().getObjectguid());
		}
		if (gar.getLev5Pr() != null && gar.getIdlev5Pr() != null) {
			attachOtherRegions.setHsguidpr(gar.getLev5Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev5Pr().longValue() && t.getIsActual()).findAny()
					.get().getObjectguid());
		}
		attachOtherRegions.setLpuId(formParams.getLpuId());
		attachOtherRegions.setLpuUnit(formParams.getLpuUnit());
		attachOtherRegions.setDoctorsnils(formParams.getDoctorSnils());
		if (formParams.getDudlPredst() != null) {
			attachOtherRegions.setPredstDoc(formParams.getDudlPredst());
		}
		attachOtherRegions.setUsr(usr);

		attachOtherRegionsRepository.save(attachOtherRegions);
	}

	public Page<AttachOtherRegions> getDataPage(AttachFormParameters formParams, Optional<Integer> page) {
		int currentPage = page.orElse(1);
		PageRequest pageRequest = PageRequest.of(currentPage - 1, PAGE_SIZE);
		LocalDate effDateMin = formParams.getEffDate().isEmpty() ? null : LocalDate.parse(formParams.getEffDate());
		LocalDate effDateMax = formParams.getExpDate().isEmpty() ? null : LocalDate.parse(formParams.getExpDate());
		
		return attachOtherRegionsDao.getDataPage(formParams.getLpuId(), formParams.getLpuUnit(),
				formParams.getDoctorSnils(), effDateMin, effDateMax, pageRequest);
	}

	public Page<AttachOtherRegions> getDataPage(Integer lpuId, Optional<Integer> page) {
		int currentPage = page.orElse(1);
		PageRequest pageRequest = PageRequest.of(currentPage - 1, PAGE_SIZE);

		return attachOtherRegionsRepository.findByLpuId(lpuId, pageRequest);
	}

	public void validate(AttachFormParameters formParams, BindingResult bindingResult) {
		if (formParams.getLpuUnit() != null && formParams.getLpuUnit().length() > 100) {
			bindingResult.rejectValue("lpuUnit", null);
		}
		if (formParams.getDoctorSnils() != null && formParams.getDoctorSnils().length() > 14) {
			bindingResult.rejectValue("doctorSnils", null);
		}
		if (!formParams.getEffDate().isEmpty() && !DateValidator.isValid(formParams.getEffDate())) {
			bindingResult.rejectValue("effDate", null);
		}
		if (!formParams.getExpDate().isEmpty() && !DateValidator.isValid(formParams.getExpDate())) {
			bindingResult.rejectValue("expDate", null);
		}
	}
}
