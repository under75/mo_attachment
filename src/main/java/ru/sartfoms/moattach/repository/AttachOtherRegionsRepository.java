package ru.sartfoms.moattach.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.sartfoms.moattach.entity.AttachOtherRegions;

public interface AttachOtherRegionsRepository extends JpaRepository<AttachOtherRegions, Long> {

	@Query("SELECT aor FROM AttachOtherRegions aor WHERE aor.pcyNum = ?1 and (aor.expDate >= sysdate or aor.expDate is null)")
	AttachOtherRegions findByPcyNum(String pcyNum);

	@Query(value = "SELECT * FROM LPUOWNER.ATTACH_OTHERREGIONS aor WHERE aor.lpuid = ?1 and (aor.expdate >= sysdate or aor.expdate is null) order by aor.effdate desc", countQuery = "SELECT count(*) FROM LPUOWNER.ATTACH_OTHERREGIONS aor WHERE aor.lpuid = ?1 and (aor.expdate >= sysdate or aor.expdate is null)", nativeQuery = true)
	Page<AttachOtherRegions> findByLpuId(Integer lpuId, PageRequest pageRequest);

	Collection<AttachOtherRegions> findAllByIdInOrderByEffDateDesc(Collection<Long> selectedRows);

}
