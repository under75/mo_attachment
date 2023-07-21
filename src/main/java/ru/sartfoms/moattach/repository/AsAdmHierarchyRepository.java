package ru.sartfoms.moattach.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.AsAdmHierarchy;

public interface AsAdmHierarchyRepository extends JpaRepository<AsAdmHierarchy, Long> {

	AsAdmHierarchy findByObjectidAndEndDateAfter(Long objectid, String now);

}
