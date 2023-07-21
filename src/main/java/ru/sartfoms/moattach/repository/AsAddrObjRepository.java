package ru.sartfoms.moattach.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.sartfoms.moattach.entity.AsAddrObj;

public interface AsAddrObjRepository extends JpaRepository<AsAddrObj, Long> {

	AsAddrObj findByObjectidAndIsActual(Long objid, boolean b);

	Collection<AsAddrObj> findByLevelOrderByName(Integer level);

	@Query("select aao from AsAddrObj aao where aao.objectid in (select distinct aah.objectid from AsAdmHierarchy aah where aah.parentobjid = ?1) order by aao.name")
	Collection<AsAddrObj> findLevelByParent(Long objectid);

	AsAddrObj findByObjectguidAndIsActual(String aoguid, boolean b);

	Collection<AsAddrObj> findByLevelAndNameContainingIgnoreCaseOrderByName(Integer lev, String flev1Rg);

	@Query("select aao from AsAddrObj aao where lower(aao.name) like lower(?2) and aao.objectid in (select distinct aah.objectid from AsAdmHierarchy aah where aah.parentobjid = ?1) order by aao.name")
	Collection<AsAddrObj> findLevelByParentAndNameContainingIgnoreCase(Long objectid, String flevRg);

}
