package ru.sartfoms.moattach.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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
		return lpuRepository.findByParentIdOrderByName(id);
	}

	public boolean isLpuChanged(Integer lpuIdNew, Integer lpuIdOld) {
		Lpu lpuOld = getById(lpuIdOld);
		Lpu lpuNew = getById(lpuIdNew);

		boolean flag = true;
		if ((lpuOld.getId().intValue() == lpuNew.getId().intValue())
				|| (lpuOld.getParentId().intValue() == 0
						&& lpuOld.getId().intValue() == lpuNew.getParentId().intValue())
				|| (lpuNew.getParentId().intValue() == 0
						&& lpuNew.getId().intValue() == lpuOld.getParentId().intValue())
				|| (lpuOld.getParentId().intValue() != 0
						&& lpuOld.getParentId().intValue() == lpuNew.getParentId().intValue())) {
			flag = false;
		}

		return flag;
	}
	
	public Collection<Integer> getIdsForCriteriaBuilder(Integer moId, Integer lpuId) {
		Collection<Integer> lpuIds = new ArrayList<>();
		if (moId != null && lpuId == null) {
			lpuIds.addAll(findByParentId(moId).stream().map(t -> t.getId())
					.collect(Collectors.toList()));
			lpuIds.add(moId);
		} else if (lpuId != null) {
			lpuIds.add(lpuId);
		}
		
		return lpuIds;
	}

}
