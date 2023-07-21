package ru.sartfoms.moattach.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.MPIError;
import ru.sartfoms.moattach.repository.MPIErrorRepository;

@Service
public class MPIErrorService {
	private final MPIErrorRepository mpiErrorRepository;

	public MPIErrorService(MPIErrorRepository mpiErrorRepository) {
		this.mpiErrorRepository = mpiErrorRepository;
	}

	public Collection<MPIError> findAllByRid(Long rid) {
		return mpiErrorRepository.findAllByRid(rid);
	}

}
