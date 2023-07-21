package ru.sartfoms.moattach.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.CompositeKey;
import ru.sartfoms.moattach.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, CompositeKey> {

	Collection<Contact> findByRid(Long rid);

	Collection<Contact> findByRidAndStatusIn(Long rid, String[] strings);

}
