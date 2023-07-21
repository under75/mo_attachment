package ru.sartfoms.moattach.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.Address;
import ru.sartfoms.moattach.entity.AsAddrObj;
import ru.sartfoms.moattach.entity.House;
import ru.sartfoms.moattach.model.ActualStatus;
import ru.sartfoms.moattach.model.Gar;
import ru.sartfoms.moattach.repository.AddressRepository;
import ru.sartfoms.moattach.repository.AsAddrObjRepository;
import ru.sartfoms.moattach.repository.AsAdmHierarchyRepository;
import ru.sartfoms.moattach.repository.HouseRepository;

@Service
public class AddressService {
	public final static String TYPE_RG = "1";
	public final static String TYPE_PR = "2";
	private final AddressRepository addressRepository;
	private final HouseRepository houseRepository;
	private final AsAddrObjRepository addrObjRepository;
	private final AsAdmHierarchyRepository admHierarchyRepository;

	public AddressService(AddressRepository addressRepository, HouseRepository houseRepository,
			AsAddrObjRepository addrObjRepository, AsAdmHierarchyRepository admHierarchyRepository) {
		this.addressRepository = addressRepository;
		this.houseRepository = houseRepository;
		this.addrObjRepository = addrObjRepository;
		this.admHierarchyRepository = admHierarchyRepository;
	}

	private Collection<AsAddrObj> findLevel1() {
		return addrObjRepository.findByLevelOrderByName(1);
	}

	private Collection<AsAddrObj> findLevelByParent(Long objectid) {
		return addrObjRepository.findLevelByParent(objectid);
	}

	private Collection<House> findHousesByParent(Long objectid) {
		return houseRepository.findHousesByParent(objectid);
	}

	public Collection<Address> findAllByRidAndAddressType(Long rid, String addrType) {
		return addressRepository.findAllByRidAndAddressTypeAndStatusIn(rid, addrType,
				new String[] { ActualStatus.ДНП.toString(), ActualStatus.ДПП.toString() });
	}

	public void initGar(Gar gar, Collection<Address> rgAddresses, Integer nrRgaddr) {
		if (nrRgaddr != null) {
			String path = "";
			Address addrRg = rgAddresses.stream().filter(t -> t.getNr() == nrRgaddr).findAny().get();
			if (addrRg.getHsguid() != null) {
				House house = houseRepository.findByObjectguidAndIsActual(addrRg.getHsguid(), true);
				if (house != null) {
					path = admHierarchyRepository
							.findByObjectidAndEndDateAfter(house.getObjectid(), LocalDate.now().toString()).getPath();
					gar.setIdlev5Rg(house.getId());
				}
			} else if (addrRg.getAoguid() != null) {
				AsAddrObj addrObj = addrObjRepository.findByObjectguidAndIsActual(addrRg.getAoguid(), true);
				if (addrObj != null)
					path = admHierarchyRepository
							.findByObjectidAndEndDateAfter(addrObj.getObjectid(), LocalDate.now().toString()).getPath();
			}
			for (String objidStr : path.split("\\.")) {
				Long objid = Long.valueOf(objidStr);
				AsAddrObj asAddrObj = addrObjRepository.findByObjectidAndIsActual(objid, true);
				if (asAddrObj == null) {

				} else if (asAddrObj.getLevel() == 1) {
					gar.setIdlev1Rg(asAddrObj.getId());
					gar.setLev2Rg(findLevelByParent(asAddrObj.getObjectid()));
				} else if (asAddrObj.getLevel() > 1 && asAddrObj.getLevel() < 6) {
					gar.setIdlev2Rg(asAddrObj.getId());
					gar.setLev3Rg(findLevelByParent(asAddrObj.getObjectid()));
				} else if (asAddrObj.getLevel() < 8 && asAddrObj.getLevel() > 5) {
					gar.setIdlev3Rg(asAddrObj.getId());
					gar.setLev4Rg(findLevelByParent(asAddrObj.getObjectid()));
				} else if (asAddrObj.getLevel() == 8) {
					if (gar.getIdlev3Rg() == null) {
						gar.setIdlev3Rg(asAddrObj.getId());
					} else {
						gar.setIdlev4Rg(asAddrObj.getId());
					}
					gar.setLev5Rg(findHousesByParent(asAddrObj.getObjectid()));
				}
			}
		}
		gar.setLev1Rg(findLevel1());
		gar.setLev1Pr(findLevel1());
	}

	public void setGarLevels(Gar gar) {
		// address Rg
		/** Level1 **/
		if (gar.getFlev1Rg().isEmpty())
			gar.setLev1Rg(findLevel1());
		else
			gar.setLev1Rg(findLevel1ByNameContainingIgnoreCase(gar.getFlev1Rg()));
		/** Level2 **/
		if (gar.getIdlev1Rg() != null) {
			AsAddrObj addrObj = gar.getLev1Rg().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev1Rg().longValue()).findAny().orElse(null);
			if (addrObj != null && addrObj.getTypename().equals("г")) {
				Collection<AsAddrObj> lev2RegCollection = new ArrayList<>();
				lev2RegCollection.add(addrObj);
				gar.setLev2Rg(lev2RegCollection);
			} else if (addrObj != null) {
				if (gar.getFlev2Rg().isEmpty()) {
					gar.setLev2Rg(findLevelByParent(addrObj.getObjectid()));
				} else {
					gar.setLev2Rg(
							findLevelByParentAndNameContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev2Rg()));
				}
			}
		}
		/** Level3 **/
		if (gar.getIdlev2Rg() != null && gar.getLev2Rg() != null) {
			AsAddrObj addrObj = gar.getLev2Rg().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev2Rg().longValue()).findAny().orElse(null);
			if (addrObj != null) {
				if (gar.getFlev3Rg().isEmpty()) {
					gar.setLev3Rg(findLevelByParent(addrObj.getObjectid()));
				} else {
					gar.setLev3Rg(
							findLevelByParentAndNameContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev3Rg()));
				}
			}
		}
		/** Level4-5 **/
		if (gar.getIdlev3Rg() != null && gar.getLev3Rg() != null) {
			AsAddrObj addrObj = gar.getLev3Rg().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev3Rg().longValue()).findAny().orElse(null);
			if (addrObj != null) {
				if (addrObj.getLevel() < 8 && gar.getLev3Rg() != null) {
					if (gar.getFlev4Rg().isEmpty()) {
						gar.setLev4Rg(findLevelByParent(addrObj.getObjectid()));
					} else {
						gar.setLev4Rg(
								findLevelByParentAndNameContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev4Rg()));
					}
					if (gar.getIdlev4Rg() != null) {
						AsAddrObj addrObj2 = gar.getLev4Rg().stream()
								.filter(t -> t.getId().longValue() == gar.getIdlev4Rg().longValue()).findAny()
								.orElse(null);
						if (addrObj2 != null)
							if (gar.getFlev5Rg().isEmpty()) {
								gar.setLev5Rg(findHousesByParent(addrObj2.getObjectid()));
							} else {
								gar.setLev5Rg(findHousesByParentAndHouseNumContainingIgnoreCase(addrObj2.getObjectid(),
										gar.getFlev5Rg()));
							}
					} else {
						if (gar.getFlev5Rg().isEmpty()) {
							gar.setLev5Rg(findHousesByParent(addrObj.getObjectid()));
						} else {
							gar.setLev5Rg(findHousesByParentAndHouseNumContainingIgnoreCase(addrObj.getObjectid(),
									gar.getFlev5Rg()));
						}
					}
				} else {
					if (gar.getFlev5Rg().isEmpty()) {
						gar.setLev5Rg(findHousesByParent(addrObj.getObjectid()));
					} else {
						gar.setLev5Rg(findHousesByParentAndHouseNumContainingIgnoreCase(addrObj.getObjectid(),
								gar.getFlev5Rg()));
					}
				}
			}
		} else if (gar.getIdlev2Rg() != null && gar.getLev2Rg() != null) {
			AsAddrObj addrObj = gar.getLev2Rg().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev2Rg().longValue()).findAny().orElse(null);
			if (addrObj != null)
				if (gar.getFlev5Rg().isEmpty()) {
					gar.setLev5Rg(findHousesByParent(addrObj.getObjectid()));
				} else {
					gar.setLev5Rg(
							findHousesByParentAndHouseNumContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev5Rg()));
				}
		}

		// address Pr
		/** Level1 **/
		if (gar.getFlev1Pr().isEmpty())
			gar.setLev1Pr(findLevel1());
		else
			gar.setLev1Pr(findLevel1ByNameContainingIgnoreCase(gar.getFlev1Pr()));
		/** Level2 **/
		if (gar.getIdlev1Pr() != null) {
			AsAddrObj addrObj = gar.getLev1Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev1Pr().longValue()).findAny().orElse(null);
			if (addrObj != null && addrObj.getTypename().equals("г")) {
				Collection<AsAddrObj> lev2PrCollection = new ArrayList<>();
				lev2PrCollection.add(addrObj);
				gar.setLev2Pr(lev2PrCollection);
			} else if (addrObj != null) {
				if (gar.getFlev2Pr().isEmpty()) {
					gar.setLev2Pr(findLevelByParent(addrObj.getObjectid()));
				} else {
					gar.setLev2Pr(
							findLevelByParentAndNameContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev2Pr()));
				}
			}
		}
		/** Level3 **/
		if (gar.getIdlev2Pr() != null && gar.getLev2Pr() != null) {
			AsAddrObj addrObj = gar.getLev2Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev2Pr().longValue()).findAny().orElse(null);
			if (addrObj != null) {
				if (gar.getFlev3Pr().isEmpty()) {
					gar.setLev3Pr(findLevelByParent(addrObj.getObjectid()));
				} else {
					gar.setLev3Pr(
							findLevelByParentAndNameContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev3Pr()));
				}
			}
		}
		/** Level4-5 **/
		if (gar.getIdlev3Pr() != null && gar.getLev3Pr() != null) {
			AsAddrObj addrObj = gar.getLev3Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev3Pr().longValue()).findAny().orElse(null);
			if (addrObj != null) {
				if (addrObj.getLevel() < 8 && gar.getLev3Pr() != null) {
					if (gar.getFlev4Pr().isEmpty()) {
						gar.setLev4Pr(findLevelByParent(addrObj.getObjectid()));
					} else {
						gar.setLev4Pr(
								findLevelByParentAndNameContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev4Pr()));
					}
					if (gar.getIdlev4Pr() != null) {
						AsAddrObj addrObj2 = gar.getLev4Pr().stream()
								.filter(t -> t.getId().longValue() == gar.getIdlev4Pr().longValue()).findAny()
								.orElse(null);
						if (addrObj2 != null)
							if (gar.getFlev5Pr().isEmpty()) {
								gar.setLev5Pr(findHousesByParent(addrObj2.getObjectid()));
							} else {
								gar.setLev5Pr(findHousesByParentAndHouseNumContainingIgnoreCase(addrObj2.getObjectid(),
										gar.getFlev5Pr()));
							}
					} else {
						if (gar.getFlev5Pr().isEmpty()) {
							gar.setLev5Pr(findHousesByParent(addrObj.getObjectid()));
						} else {
							gar.setLev5Pr(findHousesByParentAndHouseNumContainingIgnoreCase(addrObj.getObjectid(),
									gar.getFlev5Pr()));
						}
					}
				} else {
					if (gar.getFlev5Pr().isEmpty()) {
						gar.setLev5Pr(findHousesByParent(addrObj.getObjectid()));
					} else {
						gar.setLev5Pr(findHousesByParentAndHouseNumContainingIgnoreCase(addrObj.getObjectid(),
								gar.getFlev5Pr()));
					}
				}
			}
		} else if (gar.getIdlev2Pr() != null && gar.getLev2Pr() != null) {
			AsAddrObj addrObj = gar.getLev2Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev2Pr().longValue()).findAny().orElse(null);
			if (addrObj != null)
				if (gar.getFlev5Pr().isEmpty()) {
					gar.setLev5Pr(findHousesByParent(addrObj.getObjectid()));
				} else {
					gar.setLev5Pr(
							findHousesByParentAndHouseNumContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev5Pr()));
				}
		}
	}

	private Collection<House> findHousesByParentAndHouseNumContainingIgnoreCase(Long idlevRg, String flevRg) {
		return houseRepository.findHousesByParentAndHouseNumContainingIgnoreCase(idlevRg, "%" + flevRg + "%");
	}

	private Collection<AsAddrObj> findLevelByParentAndNameContainingIgnoreCase(Long objectid, String flevRg) {
		return addrObjRepository.findLevelByParentAndNameContainingIgnoreCase(objectid, "%" + flevRg + "%");
	}

	private Collection<AsAddrObj> findLevel1ByNameContainingIgnoreCase(String flev1Rg) {
		return addrObjRepository.findByLevelAndNameContainingIgnoreCaseOrderByName(1, flev1Rg);
	}

}
