package ru.sartfoms.moattach.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.sartfoms.moattach.entity.House;
import ru.sartfoms.moattach.entity.HouseId;

public interface HouseRepository extends JpaRepository<House, HouseId> {

	House findByObjectguidAndIsActual(String hsguid, boolean b);

	@Query("select h from House h where h.objectid in (select distinct aah.objectid from AsAdmHierarchy aah where aah.parentobjid = ?1) order by h.houseNum")
	Collection<House> findHousesByParent(Long objectid);

	@Query("select h from House h where lower(h.houseNum) like lower(?2) and h.objectid in (select distinct aah.objectid from AsAdmHierarchy aah where aah.parentobjid = ?1) order by h.houseNum")
	Collection<House> findHousesByParentAndHouseNumContainingIgnoreCase(Long objectid, String filter);

}
