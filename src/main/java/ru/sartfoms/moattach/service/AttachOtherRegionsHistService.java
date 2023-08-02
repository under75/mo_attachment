package ru.sartfoms.moattach.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.AttachOtherRegions;
import ru.sartfoms.moattach.entity.AttachOtherRegionsHist;
import ru.sartfoms.moattach.repository.AttachOtherRegionsHistRepository;

@Service
public class AttachOtherRegionsHistService {
	private final AttachOtherRegionsHistRepository attachOtherRegionsHistRepository;
	
	public AttachOtherRegionsHistService(AttachOtherRegionsHistRepository attachOtherRegionsHistRepository) {
		this.attachOtherRegionsHistRepository = attachOtherRegionsHistRepository;
	}

	public void save(AttachOtherRegions referenceById) {
		AttachOtherRegionsHist attachOtherRegionsHist = new AttachOtherRegionsHist();
		attachOtherRegionsHist.setAttachid(referenceById.getId());
		attachOtherRegionsHist.setLpuId(referenceById.getLpuId());
		attachOtherRegionsHist.setAoguid(referenceById.getAoguidpr());
		attachOtherRegionsHist.setHsguid(referenceById.getHsguidpr());
		attachOtherRegionsHist.setLpuUnit(referenceById.getLpuUnit());
		attachOtherRegionsHist.setDoctorSnils(referenceById.getDoctorsnils());
		attachOtherRegionsHist.setEmail(referenceById.getEmail());
		attachOtherRegionsHist.setPhone(referenceById.getPhone());
		attachOtherRegionsHist.setEditDate(LocalDateTime.now());
		attachOtherRegionsHist.setUsr(referenceById.getUsr());
		attachOtherRegionsHist.setExpDate(referenceById.getExpDate());
		
		attachOtherRegionsHistRepository.save(attachOtherRegionsHist);
	}

	
}
