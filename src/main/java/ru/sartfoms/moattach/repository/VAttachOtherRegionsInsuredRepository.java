package ru.sartfoms.moattach.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.VAttachOtherRegionsInsured;
import ru.sartfoms.moattach.repository.custom.VAttachOtherRegionsInsuredRepositoryCustom;

public interface VAttachOtherRegionsInsuredRepository extends JpaRepository<VAttachOtherRegionsInsured, Long>, VAttachOtherRegionsInsuredRepositoryCustom {

}
