package ru.sartfoms.moattach.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.MedMz;
import ru.sartfoms.moattach.entity.MedMzPk;

public interface MedMzRepository extends JpaRepository<MedMz, MedMzPk> {

	Collection<MedMz> findByLpuIdOrderByLastName(Integer lpuId);

}
