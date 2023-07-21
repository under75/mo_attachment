package ru.sartfoms.moattach.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.DudlType;


public interface DudlTypeRepository extends JpaRepository<DudlType, String> {

	Collection<DudlType> findAllByOrderByDocName();

}
