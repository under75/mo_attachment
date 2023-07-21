package ru.sartfoms.moattach.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.DudlType;
import ru.sartfoms.moattach.repository.DudlTypeRepository;

@Service
public class DudlTypeService {
	private final DudlTypeRepository dudlTypeRepository;

	public DudlTypeService(DudlTypeRepository dudlTypeRepository) {
		this.dudlTypeRepository = dudlTypeRepository;
	}

	public Collection<DudlType> findAll() {
		return dudlTypeRepository.findAllByOrderByDocName();
	}

}
