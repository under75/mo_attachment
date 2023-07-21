package ru.sartfoms.moattach.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.Address;
import ru.sartfoms.moattach.entity.CompositeKey;

public interface AddressRepository extends JpaRepository<Address, CompositeKey> {

	Collection<Address> findAllByRidAndAddressTypeAndStatusIn(Long rid, String addrType, String[] strings);

}
