package ru.sartfoms.moattach.repository.custom;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import ru.sartfoms.moattach.entity.VAttachOtherRegionsInsured;

public interface VAttachOtherRegionsInsuredRepositoryCustom {

	Page<VAttachOtherRegionsInsured> getDataPage(Boolean historical, Collection<Integer> lpuIds, PageRequest page);
}
