package ru.sartfoms.moattach.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.Contact;
import ru.sartfoms.moattach.model.ActualStatus;
import ru.sartfoms.moattach.repository.ContactRepository;

@Service
public class ContactService {
	private final ContactRepository contactRepository;

	public ContactService(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	public Collection<Contact> findByRid(Long rid) {
		return contactRepository.findByRidAndStatusIn(rid,
				new String[] { ActualStatus.ДНП.toString(), ActualStatus.ДПП.toString() });
	}
}
