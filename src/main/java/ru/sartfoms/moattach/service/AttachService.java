package ru.sartfoms.moattach.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.Attach;
import ru.sartfoms.moattach.model.ActualStatus;
import ru.sartfoms.moattach.repository.AttachRepository;

@Service
public class AttachService {
	private final AttachRepository attachRepository;

	public AttachService(AttachRepository attachRepository) {
		this.attachRepository = attachRepository;
	}

	public Collection<Attach> findByRid(Long rid) {
		return attachRepository.findByRidAndAttachStatusInAndAreaType(rid,
				new String[] { ActualStatus.ДПП.toString(), ActualStatus.ДВП.toString() }, 1);
	}
}
