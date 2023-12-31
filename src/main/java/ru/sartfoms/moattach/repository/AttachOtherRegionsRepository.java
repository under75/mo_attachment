package ru.sartfoms.moattach.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.AttachOtherRegions;
import ru.sartfoms.moattach.repository.custom.AttachOtherRegionsRepositoryCustom;

public interface AttachOtherRegionsRepository extends JpaRepository<AttachOtherRegions, Long>, AttachOtherRegionsRepositoryCustom {

	AttachOtherRegions findByPcyNumAndExpDateAfterOrderByEffDateDesc(String pcyNum, LocalDate now);

//	@Query(value = "SELECT * FROM LPUOWNER.ATTACH_OTHERREGIONS aor WHERE aor.lpuid = ?1 and (aor.expdate >= sysdate or aor.expdate is null) order by aor.effdate desc", countQuery = "SELECT count(*) FROM LPUOWNER.ATTACH_OTHERREGIONS aor WHERE aor.lpuid = ?1 and (aor.expdate >= sysdate or aor.expdate is null)", nativeQuery = true)
//	Page<AttachOtherRegions> findByLpuId(Integer lpuId, PageRequest pageRequest);

	Page<AttachOtherRegions> findByLpuIdAndExpDateAfterOrderByEffDateDesc(Integer lpuId, LocalDate now,
			PageRequest pageRequest);

	AttachOtherRegions findFirstByPcyNumOrderByExpDate(String pcyNum);

}
