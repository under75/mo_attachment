package ru.sartfoms.moattach.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.Policy;
import ru.sartfoms.moattach.model.ActualStatus;
import ru.sartfoms.moattach.repository.PolicyRepository;

@Service
public class PolicyService {
	private final PolicyRepository policyRepository;
	
	public PolicyService(PolicyRepository policyRepository) {
		this.policyRepository = policyRepository;
	}

	public Collection<Policy> findByRid(Long rid) {
		return policyRepository.findByRidAndPcyStatusIn(rid, new String[] {ActualStatus.ДНП.toString(), ActualStatus.ДПП.toString()});
	}

}
