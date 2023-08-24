package ru.sartfoms.moattach.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
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
import ru.sartfoms.moattach.model.SearchFormParameters;
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

	public AttachOtherRegions createEntity(User user, AttachFormParameters formParams, Gar gar, Person person,
			Policy policy) {
		AttachOtherRegions attachOtherRegions = new AttachOtherRegions();
		attachOtherRegions.setUsr(user.getName());
		attachOtherRegions.setDtIns(LocalDateTime.now());
		LocalDate effDate = LocalDate.parse(formParams.getEffDate());
		attachOtherRegions.setEffDate(effDate);
		attachOtherRegions.setPeriod(formParams.getPeriod());
		attachOtherRegions.setExpDate(effDate.plusDays(formParams.getPeriod()));
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

		return attachOtherRegions;
	}

	public AttachOtherRegions attach(User user, AttachFormParameters formParams, Gar gar, Person person,
			Policy policy) {
		AttachOtherRegions attachNew = createEntity(user, formParams, gar, person, policy);
		AttachOtherRegions attachLast = getLastAttachByPcyNum(
				policy.getEnp() != null ? policy.getEnp() : policy.getPcyNum());
		LocalDate now = LocalDate.now();
		if (attachLast != null && attachNew.getLpuId().intValue() == attachLast.getLpuId().intValue()
				&& (attachLast.getContract().isAfter(now) || attachLast.getContract().equals(now))) {
			attachNew.setContract(attachLast.getContract());
		} else {
			attachNew.setContract(now.plusYears(1));
		}

		return attachOtherRegionsRepository.save(attachNew);
	}

	public AttachOtherRegions getEffAttachByPcyNum(String pcyNum) {
		return attachOtherRegionsRepository.findByPcyNumAndExpDateAfterOrderByEffDateDesc(pcyNum,
				LocalDate.now().minusDays(1));
	}

	public boolean isAttachEffective(AttachOtherRegions attachOtherRegions) {
		LocalDate now = LocalDate.now();
		return attachOtherRegions.getExpDate() != null && attachOtherRegions.getExpDate().isAfter(now.minusDays(1));
	}

	public AttachOtherRegions getReferenceById(Optional<Long> attachId) {
		return attachOtherRegionsRepository.getReferenceById(attachId.get());
	}

	public void save(AttachOtherRegions attachOtherRegions) {
		attachOtherRegionsRepository.save(attachOtherRegions);
	}

	public Boolean isUnpinned(LocalDate expDate) {
		LocalDate now = LocalDate.now();

		return expDate != null && (expDate.isBefore(now));
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

		return attachOtherRegionsRepository.findByLpuIdAndExpDateAfterOrderByEffDateDesc(lpuId,
				LocalDate.now().minusDays(1), pageRequest);
	}

	public void validate(AttachFormParameters formParams, BindingResult bindingResult) {
		if (formParams.getLpuUnit() != null && formParams.getLpuUnit().length() > 100) {
			bindingResult.rejectValue("lpuUnit", null);
		}
		if (formParams.getDoctorSnils() != null && formParams.getDoctorSnils().length() > 14) {
			bindingResult.rejectValue("doctorSnils", null);
		}
		if (formParams.getEffDate() != null && !formParams.getEffDate().isEmpty()
				&& !DateValidator.isValid(formParams.getEffDate())) {
			bindingResult.rejectValue("effDate", null);
		}
		if (formParams.getExpDate() != null && !formParams.getExpDate().isEmpty()
				&& !DateValidator.isValid(formParams.getExpDate())) {
			bindingResult.rejectValue("expDate", null);
		}
	}

	public Collection<AttachOtherRegions> findByParams(Integer lpuId, String lpuUnit, String doctorSnils,
			LocalDate effDateMin, LocalDate effDateMax) {
		return attachOtherRegionsDao.findByParams(lpuId, lpuUnit, doctorSnils, effDateMin, effDateMax);
	}

	public void validate(SearchFormParameters formParams, BindingResult bindingResult) {
		if (formParams.getEffDate() != null && !formParams.getEffDate().isEmpty()
				&& !DateValidator.isValid(formParams.getEffDate())) {
			bindingResult.rejectValue("effDate", null);
		}
		if (formParams.getExpDate() != null && !formParams.getExpDate().isEmpty()
				&& !DateValidator.isValid(formParams.getExpDate())) {
			bindingResult.rejectValue("expDate", null);
		}
		if (formParams.getBirthDay() != null && !formParams.getBirthDay().isEmpty()
				&& !DateValidator.isValid(formParams.getBirthDay())) {
			bindingResult.rejectValue("birthDay", null);
		}
	}

	public Page<AttachOtherRegions> getDataPage(SearchFormParameters formParams, Optional<Integer> page) {
		int currentPage = page.orElse(1);
		PageRequest pageRequest = PageRequest.of(currentPage - 1, PAGE_SIZE);
		LocalDate effDateMin = formParams.getEffDate().isEmpty() ? null : LocalDate.parse(formParams.getEffDate());
		LocalDate effDateMax = formParams.getExpDate().isEmpty() ? null : LocalDate.parse(formParams.getExpDate());
		LocalDate birthDay = formParams.getBirthDay().isEmpty() ? null : LocalDate.parse(formParams.getBirthDay());

		return attachOtherRegionsDao.getDataPage(formParams.getMoId(), formParams.getLpuId(), formParams.getLpuUnit(),
				formParams.getDoctorSnils(), effDateMin, effDateMax, formParams.getLastName(),
				formParams.getFirstName(), formParams.getPatronymic(), birthDay, formParams.getPolicyNum(),
				pageRequest);
	}

	public AttachOtherRegions getLastAttachByPcyNum(String pcyNum) {
		return attachOtherRegionsRepository.findFirstByPcyNumOrderByExpDate(pcyNum);
	}

}
