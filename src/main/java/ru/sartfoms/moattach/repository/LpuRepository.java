package ru.sartfoms.moattach.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.Lpu;

public interface LpuRepository extends JpaRepository<Lpu, Integer> {


	Collection<Lpu> findByParentIdOrderByName(Integer id);

}
