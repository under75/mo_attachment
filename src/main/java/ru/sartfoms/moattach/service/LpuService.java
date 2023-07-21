package ru.sartfoms.moattach.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.Lpu;
import ru.sartfoms.moattach.repository.LpuRepository;

@Service
public class LpuService {
	private final LpuRepository lpuRepository;

	public LpuService(LpuRepository lpuRepository) {
		this.lpuRepository = lpuRepository;
	}

	public Lpu getById(Integer id) {
		return lpuRepository.getReferenceById(id);
	}

	public Collection<Lpu> findByParentId(Integer id) {
		return lpuRepository.findByParentIdAndFlagMOrderByName(id, 1);
	}

}
