package ru.sartfoms.moattach.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.sartfoms.moattach.entity.AttachOtherRegions;

public interface AttachOtherRegionsRepository extends JpaRepository<AttachOtherRegions, Long> {

	@Query("SELECT aor FROM AttachOtherRegions aor WHERE aor.pcyNum = ?1 and (aor.effDate >= ?2) or aor.effDate is null")
	AttachOtherRegions findByPcyNum(String pcyNum, LocalDate now);

}
