package ru.sartfoms.moattach.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.VAttachOtherRegionsInsured;
import ru.sartfoms.moattach.model.SearchFormParameters;
import ru.sartfoms.moattach.repository.VAttachOtherRegionsInsuredRepository;

@Service
public class VAttachOtherRegionsInsuredService {
	private static final Integer PAGE_SIZE = 15;
	private final VAttachOtherRegionsInsuredRepository vAttachOtherRegionsInsuredRepository;
	private final LpuService lpuService;

	public VAttachOtherRegionsInsuredService(VAttachOtherRegionsInsuredRepository vAttachOtherRegionsInsuredRepository,
			LpuService lpuService) {
		this.vAttachOtherRegionsInsuredRepository = vAttachOtherRegionsInsuredRepository;
		this.lpuService = lpuService;
	}

	public Page<VAttachOtherRegionsInsured> getDataPage(SearchFormParameters formParams, Optional<Integer> page) {
		int currentPage = page.orElse(1);
		PageRequest pageRequest = PageRequest.of(currentPage - 1, PAGE_SIZE);

		return vAttachOtherRegionsInsuredRepository.getDataPage(formParams.getHistorical(),
				lpuService.getIdsForCriteriaBuilder(formParams.getMoId(), formParams.getLpuId()), pageRequest);
	}
}
