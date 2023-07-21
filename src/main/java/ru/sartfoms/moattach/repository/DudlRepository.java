package ru.sartfoms.moattach.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.CompositeKey;
import ru.sartfoms.moattach.entity.Dudl;

public interface DudlRepository extends JpaRepository<Dudl, CompositeKey> {

	Collection<Dudl> findByRidAndDudlStatusIn(Long rid, String[] strings);

}
