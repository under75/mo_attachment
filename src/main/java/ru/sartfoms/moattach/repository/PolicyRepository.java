package ru.sartfoms.moattach.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.CompositeKey;
import ru.sartfoms.moattach.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, CompositeKey> {

	Collection<Policy> findByRidAndPcyStatusIn(Long rid, String[] strings);

}
