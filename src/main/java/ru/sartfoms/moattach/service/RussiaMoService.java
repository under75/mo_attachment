package ru.sartfoms.moattach.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.RussiaMo;
import ru.sartfoms.moattach.repository.RussiaMoRepository;

@Service
public class RussiaMoService {
	private final RussiaMoRepository russiaMoRepository;

	public RussiaMoService(RussiaMoRepository russiaMoRepository) {
		this.russiaMoRepository = russiaMoRepository;
	}

	public RussiaMo getById(String id) {
		return russiaMoRepository.getReferenceById(id);
	}

	public Collection<RussiaMo> findAll() {
		return russiaMoRepository.findAll();
	}
}
