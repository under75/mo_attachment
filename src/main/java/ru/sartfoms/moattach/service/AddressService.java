package ru.sartfoms.moattach.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.Address;
import ru.sartfoms.moattach.entity.AsAddrObj;
import ru.sartfoms.moattach.entity.AttachOtherRegions;
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
				new String[] { ActualStatus.ДПП.toString() });
	}

	public void initGarRgFromFerzl(Gar gar, Collection<Address> rgAddresses, Integer nrRgaddr) {
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
			if (!path.isEmpty())
				initGarRg(gar, path);
		}
		gar.setLev1Rg(findLevel1());
		gar.setLev1Pr(findLevel1());
	}

	private void initGarRg(Gar gar, String path) {
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

	private void initGarPr(Gar gar, String path) {
		for (String objidStr : path.split("\\.")) {
			Long objid = Long.valueOf(objidStr);
			AsAddrObj asAddrObj = addrObjRepository.findByObjectidAndIsActual(objid, true);
			if (asAddrObj == null) {

			} else if (asAddrObj.getLevel() == 1) {
				gar.setIdlev1Pr(asAddrObj.getId());
				gar.setLev2Pr(findLevelByParent(asAddrObj.getObjectid()));
			} else if (asAddrObj.getLevel() > 1 && asAddrObj.getLevel() < 6) {
				gar.setIdlev2Pr(asAddrObj.getId());
				gar.setLev3Pr(findLevelByParent(asAddrObj.getObjectid()));
			} else if (asAddrObj.getLevel() < 8 && asAddrObj.getLevel() > 5) {
				gar.setIdlev3Pr(asAddrObj.getId());
				gar.setLev4Pr(findLevelByParent(asAddrObj.getObjectid()));
			} else if (asAddrObj.getLevel() == 8) {
				if (gar.getIdlev3Pr() == null) {
					gar.setIdlev3Pr(asAddrObj.getId());
				} else {
					gar.setIdlev4Pr(asAddrObj.getId());
				}
				gar.setLev5Pr(findHousesByParent(asAddrObj.getObjectid()));
			}
		}
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
					gar.setLev2Pr(findLevelByParent(addrObj.getObjectid()).stream().filter(t -> t.getIsActual())
							.collect(Collectors.toList()));
				} else {
					gar.setLev2Pr(findLevelByParentAndNameContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev2Pr())
							.stream().filter(t -> t.getIsActual()).collect(Collectors.toList()));
				}
			}
		}
		/** Level3 **/
		if (gar.getIdlev2Pr() != null && gar.getLev2Pr() != null) {
			AsAddrObj addrObj = gar.getLev2Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev2Pr().longValue()).findAny().orElse(null);
			if (addrObj != null) {
				if (gar.getFlev3Pr().isEmpty()) {
					gar.setLev3Pr(findLevelByParent(addrObj.getObjectid()).stream().filter(t -> t.getIsActual())
							.collect(Collectors.toList()));
				} else {
					gar.setLev3Pr(findLevelByParentAndNameContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev3Pr())
							.stream().filter(t -> t.getIsActual()).collect(Collectors.toList()));
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
						gar.setLev4Pr(findLevelByParent(addrObj.getObjectid()).stream().filter(t -> t.getIsActual())
								.collect(Collectors.toList()));
					} else {
						gar.setLev4Pr(
								findLevelByParentAndNameContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev4Pr())
										.stream().filter(t -> t.getIsActual()).collect(Collectors.toList()));
					}
					if (gar.getIdlev4Pr() != null) {
						AsAddrObj addrObj2 = gar.getLev4Pr().stream()
								.filter(t -> t.getId().longValue() == gar.getIdlev4Pr().longValue()).findAny()
								.orElse(null);
						if (addrObj2 != null)
							if (gar.getFlev5Pr().isEmpty()) {
								gar.setLev5Pr(findHousesByParent(addrObj2.getObjectid()).stream()
										.filter(t -> t.getIsActual() && t.getIsActive()).collect(Collectors.toList()));
							} else {
								gar.setLev5Pr(findHousesByParentAndHouseNumContainingIgnoreCase(addrObj2.getObjectid(),
										gar.getFlev5Pr()).stream().filter(t -> t.getIsActual() && t.getIsActive())
										.collect(Collectors.toList()));
							}
					} else {
						if (gar.getFlev5Pr().isEmpty()) {
							gar.setLev5Pr(findHousesByParent(addrObj.getObjectid()).stream()
									.filter(t -> t.getIsActual() && t.getIsActive()).collect(Collectors.toList()));
						} else {
							gar.setLev5Pr(findHousesByParentAndHouseNumContainingIgnoreCase(addrObj.getObjectid(),
									gar.getFlev5Pr()).stream().filter(t -> t.getIsActual() && t.getIsActive())
									.collect(Collectors.toList()));
						}
					}
				} else {
					if (gar.getFlev5Pr().isEmpty()) {
						gar.setLev5Pr(findHousesByParent(addrObj.getObjectid()).stream()
								.filter(t -> t.getIsActual() && t.getIsActive()).collect(Collectors.toList()));
					} else {
						gar.setLev5Pr(findHousesByParentAndHouseNumContainingIgnoreCase(addrObj.getObjectid(),
								gar.getFlev5Pr()).stream().filter(t -> t.getIsActual() && t.getIsActive())
								.collect(Collectors.toList()));
					}
				}
			}
		} else if (gar.getIdlev2Pr() != null && gar.getLev2Pr() != null) {
			AsAddrObj addrObj = gar.getLev2Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev2Pr().longValue()).findAny().orElse(null);
			if (addrObj != null)
				if (gar.getFlev5Pr().isEmpty()) {
					gar.setLev5Pr(findHousesByParent(addrObj.getObjectid()).stream()
							.filter(t -> t.getIsActual() && t.getIsActive()).collect(Collectors.toList()));
				} else {
					gar.setLev5Pr(
							findHousesByParentAndHouseNumContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev5Pr())
									.stream().filter(t -> t.getIsActual() && t.getIsActive())
									.collect(Collectors.toList()));
				}
		}

		// address Mo
		/** Level1 **/
		if (gar.getFlev1Mo().isEmpty())
			gar.setLev1Mo(findLevel1().stream().filter(t -> t.getIsActual()).collect(Collectors.toList()));
		else
			gar.setLev1Mo(findLevel1ByNameContainingIgnoreCase(gar.getFlev1Mo()).stream().filter(t -> t.getIsActual())
					.collect(Collectors.toList()));
		/** Level2 **/
		if (gar.getIdlev1Mo() != null) {
			AsAddrObj addrObj = gar.getLev1Mo().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev1Mo().longValue()).findAny().orElse(null);
			if (addrObj != null && addrObj.getTypename().equals("г")) {
				Collection<AsAddrObj> lev2MoCollection = new ArrayList<>();
				lev2MoCollection.add(addrObj);
				gar.setLev2Mo(lev2MoCollection);
			} else if (addrObj != null) {
				if (gar.getFlev2Mo().isEmpty()) {
					gar.setLev2Mo(findLevelByParent(addrObj.getObjectid()).stream().filter(t -> t.getIsActual())
							.collect(Collectors.toList()));
				} else {
					gar.setLev2Mo(findLevelByParentAndNameContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev2Mo())
							.stream().filter(t -> t.getIsActual()).collect(Collectors.toList()));
				}
			}
		}
		/** Level3 **/
		if (gar.getIdlev2Mo() != null && gar.getLev2Mo() != null) {
			AsAddrObj addrObj = gar.getLev2Mo().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev2Mo().longValue()).findAny().orElse(null);
			if (addrObj != null) {
				if (gar.getFlev3Mo().isEmpty()) {
					gar.setLev3Mo(findLevelByParent(addrObj.getObjectid()).stream().filter(t -> t.getIsActual())
							.collect(Collectors.toList()));
				} else {
					gar.setLev3Mo(findLevelByParentAndNameContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev3Mo())
							.stream().filter(t -> t.getIsActual()).collect(Collectors.toList()));
				}
			}
		}
		/** Level4-5 **/
		if (gar.getIdlev3Mo() != null && gar.getLev3Mo() != null) {
			AsAddrObj addrObj = gar.getLev3Mo().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev3Mo().longValue()).findAny().orElse(null);
			if (addrObj != null) {
				if (addrObj.getLevel() < 8 && gar.getLev3Mo() != null) {
					if (gar.getFlev4Mo().isEmpty()) {
						gar.setLev4Mo(findLevelByParent(addrObj.getObjectid()).stream().filter(t -> t.getIsActual())
								.collect(Collectors.toList()));
					} else {
						gar.setLev4Mo(
								findLevelByParentAndNameContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev4Mo())
										.stream().filter(t -> t.getIsActual()).collect(Collectors.toList()));
					}
					if (gar.getIdlev4Mo() != null) {
						AsAddrObj addrObj2 = gar.getLev4Mo().stream()
								.filter(t -> t.getId().longValue() == gar.getIdlev4Mo().longValue()).findAny()
								.orElse(null);
						if (addrObj2 != null)
							if (gar.getFlev5Mo().isEmpty()) {
								gar.setLev5Mo(findHousesByParent(addrObj2.getObjectid()).stream()
										.filter(t -> t.getIsActual() && t.getIsActive()).collect(Collectors.toList()));
							} else {
								gar.setLev5Mo(findHousesByParentAndHouseNumContainingIgnoreCase(addrObj2.getObjectid(),
										gar.getFlev5Mo()).stream().filter(t -> t.getIsActual() && t.getIsActive())
										.collect(Collectors.toList()));
							}
					} else {
						if (gar.getFlev5Mo().isEmpty()) {
							gar.setLev5Mo(findHousesByParent(addrObj.getObjectid()).stream()
									.filter(t -> t.getIsActual() && t.getIsActive()).collect(Collectors.toList()));
						} else {
							gar.setLev5Mo(findHousesByParentAndHouseNumContainingIgnoreCase(addrObj.getObjectid(),
									gar.getFlev5Mo()).stream().filter(t -> t.getIsActual() && t.getIsActive())
									.collect(Collectors.toList()));
						}
					}
				} else {
					if (gar.getFlev5Mo().isEmpty()) {
						gar.setLev5Mo(findHousesByParent(addrObj.getObjectid()).stream()
								.filter(t -> t.getIsActual() && t.getIsActive()).collect(Collectors.toList()));
					} else {
						gar.setLev5Mo(findHousesByParentAndHouseNumContainingIgnoreCase(addrObj.getObjectid(),
								gar.getFlev5Mo()).stream().filter(t -> t.getIsActual() && t.getIsActive())
								.collect(Collectors.toList()));
					}
				}
			}
		} else if (gar.getIdlev2Mo() != null && gar.getLev2Mo() != null) {
			AsAddrObj addrObj = gar.getLev2Mo().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev2Mo().longValue()).findAny().orElse(null);
			if (addrObj != null)
				if (gar.getFlev5Mo().isEmpty()) {
					gar.setLev5Mo(findHousesByParent(addrObj.getObjectid()).stream()
							.filter(t -> t.getIsActual() && t.getIsActive()).collect(Collectors.toList()));
				} else {
					gar.setLev5Mo(
							findHousesByParentAndHouseNumContainingIgnoreCase(addrObj.getObjectid(), gar.getFlev5Mo())
									.stream().filter(t -> t.getIsActual() && t.getIsActive())
									.collect(Collectors.toList()));
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

	public void initGar(Gar gar, String aoguidreg, String aoguidpr, String hsguidreg, String hsguidpr) {
		String path = null;
		if (hsguidreg != null) {
			House house = houseRepository.findByObjectguidAndIsActual(hsguidreg, true);
			if (house != null) {
				path = admHierarchyRepository
						.findByObjectidAndEndDateAfter(house.getObjectid(), LocalDate.now().toString()).getPath();
				gar.setIdlev5Rg(house.getId());
			}
		} else if (aoguidreg != null) {
			AsAddrObj addrObj = addrObjRepository.findByObjectguidAndIsActual(aoguidreg, true);
			if (addrObj != null)
				path = admHierarchyRepository
						.findByObjectidAndEndDateAfter(addrObj.getObjectid(), LocalDate.now().toString()).getPath();
		}
		if (path != null) {
			initGarRg(gar, path);
		}
		path = null;
		if (hsguidpr != null) {
			House house = houseRepository.findByObjectguidAndIsActual(hsguidpr, true);
			if (house != null) {
				path = admHierarchyRepository
						.findByObjectidAndEndDateAfter(house.getObjectid(), LocalDate.now().toString()).getPath();
				gar.setIdlev5Pr(house.getId());
			}
		} else if (aoguidpr != null) {
			AsAddrObj addrObj = addrObjRepository.findByObjectguidAndIsActual(aoguidpr, true);
			if (addrObj != null)
				path = admHierarchyRepository
						.findByObjectidAndEndDateAfter(addrObj.getObjectid(), LocalDate.now().toString()).getPath();
		}
		if (path != null) {
			initGarPr(gar, path);
		}
		gar.setLev1Rg(findLevel1());
//		gar.setLev1Pr(findLevel1());
		gar.setLev1Pr(findLevel1ByNameContainingIgnoreCase(gar.getFlev1Pr()));
	}

	public String getAddrRgStr(Gar gar) {
		StringBuilder result = new StringBuilder();
		if (gar.getIdlev1Rg() != null && gar.getLev1Rg() != null) {
			result.append(gar.getLev1Rg().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev1Rg().longValue() && t.getIsActual())
					.map(t -> t.getName() + " " + t.getTypename()).findFirst().orElse("").trim());
		}
		if (gar.getIdlev2Rg() != null && gar.getLev2Rg() != null) {
			result.append(", " + gar.getLev2Rg().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev2Rg().longValue() && t.getIsActual())
					.map(t -> t.getName() + " " + t.getTypename()).findFirst().orElse("").trim());
		}
		if (gar.getIdlev3Rg() != null && gar.getLev3Rg() != null) {
			result.append(", " + gar.getLev3Rg().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev3Rg().longValue() && t.getIsActual())
					.map(t -> t.getName() + " " + t.getTypename()).findFirst().orElse("").trim());
		}
		if (gar.getIdlev4Rg() != null && gar.getLev4Rg() != null) {
			result.append(", " + gar.getLev4Rg().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev4Rg() && t.getIsActual())
					.map(t -> t.getName() + " " + t.getTypename()).findFirst().orElse("").trim());
		}
		if (gar.getIdlev5Rg() != null && gar.getLev5Rg() != null) {
			result.append(", " + gar.getLev5Rg().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev5Rg().longValue() && t.getIsActual())
					.map(t -> t.getHouseNum() + " " + (t.getAddNum1() != null ? t.getAddNum1() : "") + " "
							+ (t.getAddNum2() != null ? t.getAddNum2() : ""))
					.findFirst().orElse("").trim());
		}

		return result.toString();
	}

	public String getAddrPrStr(Gar gar) {
		StringBuilder result = new StringBuilder();
		if (gar.getIdlev1Pr() != null && gar.getLev1Pr() != null) {
			result.append(gar.getLev1Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev1Pr().longValue() && t.getIsActual())
					.map(t -> t.getName() + " " + t.getTypename()).findFirst().orElse("").trim());
		}
		if (gar.getIdlev2Pr() != null && gar.getLev2Pr() != null) {
			result.append(", " + gar.getLev2Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev2Pr().longValue() && t.getIsActual())
					.map(t -> t.getName() + " " + t.getTypename()).findFirst().orElse("").trim());
		}
		if (gar.getIdlev3Pr() != null && gar.getLev3Pr() != null) {
			result.append(", " + gar.getLev3Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev3Pr().longValue() && t.getIsActual())
					.map(t -> t.getName() + " " + t.getTypename()).findFirst().orElse("").trim());
		}
		if (gar.getIdlev4Pr() != null && gar.getLev4Pr() != null) {
			result.append(", " + gar.getLev4Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev4Pr() && t.getIsActual())
					.map(t -> t.getName() + " " + t.getTypename()).findFirst().orElse("").trim());
		}
		if (gar.getIdlev5Pr() != null && gar.getLev5Pr() != null) {
			result.append(", " + gar.getLev5Pr().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev5Pr().longValue() && t.getIsActual())
					.map(t -> t.getHouseNum() + " " + (t.getAddNum1() != null ? t.getAddNum1() : "") + " "
							+ (t.getAddNum2() != null ? t.getAddNum2() : ""))
					.findFirst().orElse("").trim());
		}

		return result.toString();
	}

	public String getAddrStr(String aoguid, String hsguid) {
		StringBuilder builder = new StringBuilder();
		String path = null;
		if (hsguid != null) {
			House house = houseRepository.findByObjectguidAndIsActual(hsguid, true);
			if (house != null) {
				path = admHierarchyRepository
						.findByObjectidAndEndDateAfter(house.getObjectid(), LocalDate.now().toString()).getPath();
			}
		} else if (aoguid != null) {
			AsAddrObj addrObj = addrObjRepository.findByObjectguidAndIsActual(aoguid, true);
			if (addrObj != null)
				path = admHierarchyRepository
						.findByObjectidAndEndDateAfter(addrObj.getObjectid(), LocalDate.now().toString()).getPath();
		}
		if (path != null) {
			for (String objidStr : path.split("\\.")) {
				Long objid = Long.valueOf(objidStr);
				AsAddrObj asAddrObj = addrObjRepository.findByObjectidAndIsActual(objid, true);
				if (asAddrObj == null) {
					House house = houseRepository.findByObjectidAndIsActual(objid, true);
					builder.append(house.getHouseNum() + (house.getAddNum1() != null ? " " + house.getAddNum1() : "")
							+ (house.getAddNum2() != null ? " " + house.getAddNum2() : ""));
				} else {
					builder.append(asAddrObj.getName() + " " + asAddrObj.getTypename() + ",");
				}
			}
		}

		return builder.toString().trim();
	}

	public boolean isAddrPrModified(Gar gar, AttachOtherRegions attachOtherRegions) {
		String hsguidprOld = attachOtherRegions.getHsguidpr();
		String hsguidprNew = houseRepository.findByIdAndIsActual(gar.getIdlev5Pr(), true).getObjectguid();

		return !hsguidprOld.equals(hsguidprNew);
	}

	public LocalDate findAddressDateB(Gar gar, Long rid) {
		Collection<Address> addresses = findAllByRidAndAddressType(rid, AddressService.TYPE_RG);

		Address address = addresses.stream()
				.filter(t -> t.getHsguid() != null && t.getHsguid()
						.equals(gar.getLev5Rg().stream()
								.filter(o -> o.getId().longValue() == gar.getIdlev5Rg().longValue()).findAny().get()
								.getObjectguid()))
				.findAny().orElse(null);

		return address != null ? address.getAddressDateB() : null;
	}

	public String getAddrMoStr(Gar gar) {
		StringBuilder result = new StringBuilder();
		if (gar.getIdlev1Mo() != null) {
			result.append(gar.getLev1Mo().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev1Mo().longValue() && t.getIsActual())
					.map(t -> t.getName() + " " + t.getTypename()).findFirst().orElse("").trim());
		}
		if (gar.getIdlev2Mo() != null) {
			result.append(", " + gar.getLev2Mo().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev2Mo().longValue() && t.getIsActual())
					.map(t -> t.getName() + " " + t.getTypename()).findFirst().orElse("").trim());
		}
		if (gar.getIdlev3Mo() != null) {
			result.append(", " + gar.getLev3Mo().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev3Mo().longValue() && t.getIsActual())
					.map(t -> t.getName() + " " + t.getTypename()).findFirst().orElse("").trim());
		}
		if (gar.getIdlev4Mo() != null && gar.getLev4Mo() != null) {
			result.append(", " + gar.getLev4Mo().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev4Mo() && t.getIsActual())
					.map(t -> t.getName() + " " + t.getTypename()).findFirst().orElse("").trim());
		}
		if (gar.getIdlev5Mo() != null) {
			result.append(", " + gar.getLev5Mo().stream()
					.filter(t -> t.getId().longValue() == gar.getIdlev5Mo().longValue() && t.getIsActual())
					.map(t -> t.getHouseNum() + " " + (t.getAddNum1() != null ? t.getAddNum1() : "") + " "
							+ (t.getAddNum2() != null ? t.getAddNum2() : ""))
					.findFirst().orElse("").trim());
		}

		return result.toString();
	}

}
