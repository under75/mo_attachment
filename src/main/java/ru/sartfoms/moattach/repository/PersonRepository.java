package ru.sartfoms.moattach.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.CompositeKey;
import ru.sartfoms.moattach.entity.Person;

public interface PersonRepository extends JpaRepository<Person, CompositeKey> {

	Collection<Person> findAllByRid(Long rid);

	Collection<Person> findByRidAndStatusIn(Long rid, String[] strings);

}
