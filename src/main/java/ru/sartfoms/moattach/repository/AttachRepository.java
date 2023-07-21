package ru.sartfoms.moattach.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.Attach;
import ru.sartfoms.moattach.entity.CompositeKey;

public interface AttachRepository extends JpaRepository<Attach, CompositeKey> {

	Collection<Attach> findByRidAndAttachStatusInAndAreaType(Long rid, String[] strings, int i);

}
