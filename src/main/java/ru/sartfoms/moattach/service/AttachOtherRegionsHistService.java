package ru.sartfoms.moattach.service;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.repository.AttachOtherRegionsHistRepository;

@Service
public class AttachOtherRegionsHistService {
	private final AttachOtherRegionsHistRepository attachOtherRegionsHistRepository;
	
	public AttachOtherRegionsHistService(AttachOtherRegionsHistRepository attachOtherRegionsHistRepository) {
		this.attachOtherRegionsHistRepository = attachOtherRegionsHistRepository;
	}
}
