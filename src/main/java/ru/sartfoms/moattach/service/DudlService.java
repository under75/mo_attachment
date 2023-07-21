package ru.sartfoms.moattach.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.Dudl;
import ru.sartfoms.moattach.model.ActualStatus;
import ru.sartfoms.moattach.repository.DudlRepository;

@Service
public class DudlService {
	private final DudlRepository dudlRepository;

	public DudlService(DudlRepository dudlRepository) {
		this.dudlRepository = dudlRepository;
	}

	public Collection<Dudl> findByRid(Long rid) {
		return dudlRepository.findByRidAndDudlStatusIn(rid,
				new String[] { ActualStatus.ДНП.toString(), ActualStatus.ДПП.toString() });
	}

}
