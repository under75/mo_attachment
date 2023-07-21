package ru.sartfoms.moattach.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.CompositeKey;
import ru.sartfoms.moattach.entity.MPIError;

public interface MPIErrorRepository extends JpaRepository<MPIError, CompositeKey> {

	Collection<MPIError> findAllByRid(Long rid);

}
