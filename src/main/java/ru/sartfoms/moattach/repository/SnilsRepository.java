package ru.sartfoms.moattach.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.CompositeKey;
import ru.sartfoms.moattach.entity.Snils;

public interface SnilsRepository extends JpaRepository<Snils, CompositeKey> {

	Collection<Snils> findAllByRid(Long rid);

}
