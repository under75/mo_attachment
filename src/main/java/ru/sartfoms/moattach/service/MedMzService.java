package ru.sartfoms.moattach.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.MedMz;
import ru.sartfoms.moattach.repository.MedMzRepository;

@Service
public class MedMzService {
	private final MedMzRepository medMzRepository;

	public MedMzService(MedMzRepository medMzRepository) {
		this.medMzRepository = medMzRepository;
	}

	public Collection<MedMz> findByLpuId(Integer lpuId) {
		return medMzRepository.findByLpuIdOrderByLastName(lpuId);
	}
}
