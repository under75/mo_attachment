package ru.sartfoms.moattach.service;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.repository.AttachOtherRegionsRepository;

@Service
public class AttachOtherRegionsService {
	private final AttachOtherRegionsRepository attachOtherRegionsRepository;

	public AttachOtherRegionsService(AttachOtherRegionsRepository attachOtherRegionsRepository) {
		this.attachOtherRegionsRepository = attachOtherRegionsRepository;
	}
}
