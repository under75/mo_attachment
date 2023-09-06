package ru.sartfoms.moattach.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.Snils;
import ru.sartfoms.moattach.model.ActualStatus;
import ru.sartfoms.moattach.repository.SnilsRepository;

@Service
public class SnilsService {
	private final SnilsRepository snilsRepository;
	
	public SnilsService(SnilsRepository snilsRepository) {
		this.snilsRepository = snilsRepository;
	}

	public Collection<Snils> findAllByRid(Long rid) {
		return snilsRepository.findAllByRidAndStatusIn(rid, new String[] {ActualStatus.ДНП.toString(), ActualStatus.ДПП.toString()});
	}
}
