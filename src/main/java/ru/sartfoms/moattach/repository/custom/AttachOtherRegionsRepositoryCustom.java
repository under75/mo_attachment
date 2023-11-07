package ru.sartfoms.moattach.repository.custom;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import ru.sartfoms.moattach.entity.AttachOtherRegions;

public interface AttachOtherRegionsRepositoryCustom {
	
	Page<AttachOtherRegions> getDataPage(Boolean historical, Collection<Integer> lpuIds, String lpuUnit,
			String doctorSnils, LocalDate effDateMin, LocalDate effDateMax, PageRequest page);
	
	Page<AttachOtherRegions> getDataPage(Boolean historical, Collection<Integer> lpuIds, String lpuUnit,
			String doctorSnils, LocalDate effDateMin, LocalDate effDateMax, String lastName, String firstName,
			String patronymic, LocalDate birthDay, String policyNum, PageRequest page);
	
	Collection<AttachOtherRegions> findByParams(Boolean historical, Collection<Integer> lpuIds, String lpuUnit,
			String doctorSnils, LocalDate effDateMin, LocalDate effDateMax, String lastName, String firstName,
			String patronymic, LocalDate birthday, String policyNum);
}
