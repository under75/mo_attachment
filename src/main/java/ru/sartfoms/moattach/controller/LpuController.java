package ru.sartfoms.moattach.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.sartfoms.moattach.entity.Address;
import ru.sartfoms.moattach.entity.Attach;
import ru.sartfoms.moattach.entity.AttachOtherRegions;
import ru.sartfoms.moattach.entity.Contact;
import ru.sartfoms.moattach.entity.Dudl;
import ru.sartfoms.moattach.entity.Lpu;
import ru.sartfoms.moattach.entity.MPIError;
import ru.sartfoms.moattach.entity.MedMz;
import ru.sartfoms.moattach.entity.MedMzPk;
import ru.sartfoms.moattach.entity.Person;
import ru.sartfoms.moattach.entity.PersonData;
import ru.sartfoms.moattach.entity.Policy;
import ru.sartfoms.moattach.entity.RussiaMo;
import ru.sartfoms.moattach.entity.Snils;
import ru.sartfoms.moattach.entity.User;
import ru.sartfoms.moattach.exception.ExcelGeneratorException;
import ru.sartfoms.moattach.model.AttachFormParameters;
import ru.sartfoms.moattach.model.FerzlSearchParameters;
import ru.sartfoms.moattach.model.Gar;
import ru.sartfoms.moattach.model.PolicyTypes;
import ru.sartfoms.moattach.service.AddressService;
import ru.sartfoms.moattach.service.AttachOtherRegionsHistService;
import ru.sartfoms.moattach.service.AttachOtherRegionsService;
import ru.sartfoms.moattach.service.AttachService;
import ru.sartfoms.moattach.service.ContactService;
import ru.sartfoms.moattach.service.DudlService;
import ru.sartfoms.moattach.service.DudlTypeService;
import ru.sartfoms.moattach.service.ExcelService;
import ru.sartfoms.moattach.service.LpuService;
import ru.sartfoms.moattach.service.MPIErrorService;
import ru.sartfoms.moattach.service.MedMzService;
import ru.sartfoms.moattach.service.PersonDataService;
import ru.sartfoms.moattach.service.PersonService;
import ru.sartfoms.moattach.service.PolicyService;
import ru.sartfoms.moattach.service.RussiaMoService;
import ru.sartfoms.moattach.service.SnilsService;
import ru.sartfoms.moattach.service.UserService;
import ru.sartfoms.moattach.service.WordService;
import ru.sartfoms.moattach.util.DateValidator;
import ru.sartfoms.moattach.util.Info;

@Controller
@RequestMapping("/lpu")
public class LpuController {
	private final UserService userService;
	private final LpuService lpuService;
	private final DudlTypeService dudlTypeService;
	private final PersonDataService personDataService;
	private final MPIErrorService mpiErrorService;
	private final PersonService personService;
	private final PolicyService policyService;
	private final DudlService dudlService;
	private final AddressService addressService;
	private final AttachService attachService;
	private final ContactService contactService;
	private final MedMzService medMzService;
	private final AttachOtherRegionsService attachOtherRegionsService;
	private final AttachOtherRegionsHistService attachOtherRegionsHistService;
	private final ExcelService excelService;
	private final WordService wordService;
	private final SnilsService snilsService;
	private final RussiaMoService russiaMoService;
	@Autowired
	SmartValidator validator;
	@Autowired
	Info info;

	public LpuController(UserService userService, LpuService lpuService, DudlTypeService dudlTypeService,
			PersonDataService personDataService, MPIErrorService mpiErrorService, PersonService personService,
			PolicyService policyService, DudlService dudlService, AddressService addressService,
			AttachService attachService, ContactService contactService, MedMzService medMzService,
			AttachOtherRegionsService attachOtherRegionsService,
			AttachOtherRegionsHistService attachOtherRegionsHistService, ExcelService excelService,
			WordService wordService, SnilsService snilsService, RussiaMoService russiaMoService) {
		this.userService = userService;
		this.lpuService = lpuService;
		this.dudlTypeService = dudlTypeService;
		this.personDataService = personDataService;
		this.mpiErrorService = mpiErrorService;
		this.personService = personService;
		this.policyService = policyService;
		this.dudlService = dudlService;
		this.addressService = addressService;
		this.attachService = attachService;
		this.contactService = contactService;
		this.medMzService = medMzService;
		this.attachOtherRegionsService = attachOtherRegionsService;
		this.attachOtherRegionsHistService = attachOtherRegionsHistService;
		this.excelService = excelService;
		this.wordService = wordService;
		this.snilsService = snilsService;
		this.russiaMoService = russiaMoService;
	}
	
	@ModelAttribute
	public void addInfoToModel(Model model) {
		model.addAttribute("info", info);
	}

	@GetMapping("/ferzl")
	public String getPersonData(Model model) {

		User user = userService.getByName(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("lpu", lpuService.getById(user.getLpuId()));
		model.addAttribute("policyTypes", PolicyTypes.getInstance());
		model.addAttribute("dudlTypes", dudlTypeService.findAll());

		FerzlSearchParameters formParams = new FerzlSearchParameters();
		model.addAttribute("searchParams", formParams);

		Optional<Integer> page = Optional.of(1);
		Page<PersonData> dataPage = personDataService.getDataPage(formParams, user.getName(), page);
		model.addAttribute("dataPage", dataPage);

		return "lpu-ferzl";
	}

	@PostMapping("/ferzl")
	public String getPersonData(Model model, @ModelAttribute("searchParams") FerzlSearchParameters searchParams,
			BindingResult bindingResult, @RequestParam("page") Optional<Integer> page) {

		User user = userService.getByName(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("lpu", lpuService.getById(user.getLpuId()));
		model.addAttribute("policyTypes", PolicyTypes.getInstance());
		model.addAttribute("dudlTypes", dudlTypeService.findAll());

		if (!page.isPresent()) {
			personDataService.validate(searchParams, bindingResult);
			validator.validate(searchParams, bindingResult);
		}

		if (!bindingResult.hasErrors() && !page.isPresent())
			personDataService.saveRequest(searchParams, user.getName());

		Page<PersonData> dataPage = personDataService.getDataPage(searchParams, user.getName(), page);
		model.addAttribute("dataPage", dataPage);

		return "lpu-ferzl";
	}

	@PostMapping("/attach")
	public String attachPersonToLpu(Model model, @ModelAttribute("gar") Gar gar, BindingResult bindingResult2,
			@RequestParam("rid") Long rid, @ModelAttribute("formParams") AttachFormParameters formParams,
			BindingResult bindingResult, @RequestParam("rgaddr") Optional<Integer> rgaddr,
			@RequestParam("cntnr") Optional<Integer> cntnr, @RequestParam("nrdudl") Optional<Integer> nrdudl,
			@RequestParam("save") Optional<String> save, @RequestParam("print") Optional<String> print,
			HttpSession session) {

		model.addAttribute("rid", rid);
		User user = userService.getByName(SecurityContextHolder.getContext().getAuthentication().getName());
		Lpu lpu = lpuService.getById(user.getLpuId());
		model.addAttribute("lpu", lpu);

		Collection<MPIError> errors = mpiErrorService.findAllByRid(rid);
		if (errors.size() > 0) {
			model.addAttribute("errors", errors);
			return "mpi-err";
		}

		Collection<Lpu> lpus = new ArrayList<>();
		lpus.add(lpu);
		if (lpu.getParentId() == 0
				&& user.getRoles().stream().anyMatch(t -> t.getRoleName().contains("admin"))) {
			lpus.addAll(lpuService.findByParentId(lpu.getId()));
		}
		model.addAttribute("lpus", lpus);

		Policy policy = policyService.findByRid(rid).stream().max(Comparator.comparing(Policy::getPcyDateB)).get();
		AttachOtherRegions attachOtherRegions = attachOtherRegionsService
				.getEffAttachByPcyNum(policy.getEnp() != null ? policy.getEnp() : policy.getPcyNum());
		Person person = personService.findByRid(rid).stream().findAny().get();
		model.addAttribute("person", person);
		model.addAttribute("attachOtherRegions", attachOtherRegions);
		model.addAttribute("policy", policy);
		model.addAttribute("policyTypes", PolicyTypes.getInstance());
		Attach attach = attachService.findByRid(rid).stream().findAny().orElse(null);
		AttachOtherRegions attachLast = attachOtherRegionsService
				.getLastAttachByPcyNum(policy.getEnp() != null ? policy.getEnp() : policy.getPcyNum());
		if (attachOtherRegions != null && formParams.getMoName() == null) {
			formParams.setMoName(lpuService.getById(attachOtherRegions.getLpuId()).getName());
		} else if (attachLast != null && formParams.getMoName() == null) {
			formParams.setMoName(lpuService.getById(attachLast.getLpuId()).getName());
		} else if (attach != null) {
			RussiaMo mo = russiaMoService.getById(attach.getMocode());
			if (formParams.getMoName() == null && mo != null) {
				formParams.setMoName(mo.getShortName());
			}
		}
		model.addAttribute("moNames", russiaMoService.findAll().stream().filter(t->t.getShortName().length() > 2).map(t->t.getShortName()).collect(Collectors.toSet()));
		if (formParams.getSnils() == null) {
			Snils snils = snilsService.findAllByRid(rid).stream().findAny().orElse(null); 
			formParams.setSnils(snils != null ? snils.getSnils() : null);
		}
		Collection<Contact> contacts = contactService.findByRid(rid);
		model.addAttribute("contacts", contacts);
		if (cntnr.isPresent()) {
			Contact contact = contacts.stream().filter(t -> t.getNr().intValue() == cntnr.get().intValue()).findFirst()
					.get();
			if (contact.getContactType().contains("TEL"))
				formParams.setPhone(contact.getContactText());
			else if (contact.getContactType().contains("MAIL"))
				formParams.setEmail(contact.getContactText());
		}
		Collection<Dudl> dudls;
		try {
			dudls = dudlService.findByRid(rid);
		} catch (Exception e) {
			dudls = new ArrayList<>();
		}
		model.addAttribute("dudls", dudls);
		model.addAttribute("dudlTypes", dudlTypeService.findAll());
		if (nrdudl.isPresent()) {
			Dudl dudl = dudls.stream().filter(t -> t.getNr().intValue() == nrdudl.get().intValue()).findFirst().get();
			formParams.setDudlType(dudl.getDudlTypeStr());
			formParams.setDudlSer(dudl.getDudlSer());
			formParams.setDudlNum(dudl.getDudlNum());
		}

		Collection<Address> rgAddresses = addressService.findAllByRidAndAddressType(rid, AddressService.TYPE_RG);
		model.addAttribute("rgAddresses", rgAddresses);

		if (rgaddr.isPresent()) {
			// initialize from FERZL by the registration address
			addressService.initGarRgFromFerzl(gar, rgAddresses, rgaddr.get());
		}
		// set by UI
		addressService.setGarLevels(gar);

		Collection<MedMz> doctors = new ArrayList<MedMz>();
		if (formParams.getLpuId() != null) {
			Integer parentId = lpuService.getById(formParams.getLpuId()).getParentId();
			doctors = medMzService.findByLpuId(parentId != 0 ? parentId : formParams.getLpuId());
		}
		model.addAttribute("doctors", doctors);

		model.addAttribute("personAge", personService.getAge(person));

		if (save.isPresent() || print.isPresent()) {
			validator.validate(formParams, bindingResult);
			validator.validate(gar, bindingResult2);
			if (!DateValidator.isValid(formParams.getEffDate())
					|| DateValidator.isAfterTnanNow(formParams.getEffDate()))
				bindingResult.rejectValue("effDate", null);
			if ((int) model.getAttribute("personAge") < PersonService.MAX_CHILD_AGE) {
				if (formParams.getDudlPredst().isEmpty())
					bindingResult.rejectValue("dudlPredst", null);
			}
			if (formParams.getMoName() != null && !formParams.getMoName().isEmpty() && gar.getIdlev5Mo() == null) {
				bindingResult2.rejectValue("idlev5Mo", null);
			}
			LocalDate now = LocalDate.now();
			if (attachLast != null && formParams.getLpuId() != null
					&& lpuService.isLpuChanged(formParams.getLpuId(), attachLast.getLpuId())
					&& (attachLast.getContract().isAfter(now) || attachLast.getContract().equals(now))
					&& gar.getLev5Pr() != null && !addressService.isAddrPrModified(gar, attachLast)) {
				bindingResult.addError(new ObjectError("globalError",
						"Персона была прикреплена к другой МО. Гражданин может поменять МО только при смене адреса проживания либо после "
								+ attachLast.getContract().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
			} else if (!bindingResult.hasErrors() && !bindingResult2.hasErrors()) {
				if (print.isPresent()) {
					return "forward:/lpu/attach/word";
				}
				AttachOtherRegions attachOtherRegionsEff = attachOtherRegionsService.attach(user, formParams, gar,
						person, policy, (byte[]) session.getAttribute("worddoc"));
				model.addAttribute("attachOtherRegions", attachOtherRegionsEff);
				if (attachOtherRegions != null) {
					attachOtherRegions.setExpDate(attachOtherRegionsEff.getEffDate().minusDays(1));
					attachOtherRegionsService.save(attachOtherRegions);
				}
				attachOtherRegions = attachOtherRegionsEff;
			}
		}

		Boolean personAttached = attachOtherRegions != null
				&& !lpuService.isLpuChanged(lpu.getId(), attachOtherRegions.getLpuId())
				&& attachOtherRegionsService.isAttachEffective(attachOtherRegions);
		model.addAttribute("personAttached", personAttached);

		return "lpu-attach";
	}

	@PostMapping("/attach/edit")
	public String editAttachment(Model model, @RequestParam("attachId") Optional<Long> attachId,
			@ModelAttribute("gar") Gar gar, BindingResult bindingResult2,
			@ModelAttribute("formParams") AttachFormParameters formParams, BindingResult bindingResult,
			@RequestParam("cancel") Optional<?> cancel, @RequestParam("unpin") Optional<?> unpin,
			@RequestParam("edit") Optional<?> edit) {
		model.addAttribute("attachId", attachId.get());
		User user = userService.getByName(SecurityContextHolder.getContext().getAuthentication().getName());
		Lpu lpu = lpuService.getById(user.getLpuId());
		model.addAttribute("lpu", lpu);
		AttachOtherRegions attachOtherRegions = attachOtherRegionsService.getReferenceById(attachId);
		model.addAttribute("attachOtherRegions", attachOtherRegions);
		model.addAttribute("policyTypes", PolicyTypes.getInstance());
		model.addAttribute("dudlType", dudlTypeService.findOne(attachOtherRegions.getDudlType()));

		Collection<Lpu> lpus = new ArrayList<>();
		Lpu attachedLpu = lpuService.getById(attachOtherRegions.getLpuId());
		lpus.add(attachedLpu);
		if (lpu.getId().intValue() != attachedLpu.getId().intValue()) {
			lpus.add(lpu);
		}
		if (lpu.getParentId() == 0
				&& user.getRoles().stream().anyMatch(t -> t.getRoleName().contains("admin"))) {
			lpus.addAll(lpuService.findByParentId(lpu.getId()));
		}
		model.addAttribute("lpus", lpus);

		// init block
		if (gar.getIdlev1Pr() == null || cancel.isPresent()) {
			// initialize from AttachOtherRegions
			addressService.initGar(gar, attachOtherRegions.getAoguidreg(), attachOtherRegions.getAoguidpr(),
					attachOtherRegions.getHsguidreg(), attachOtherRegions.getHsguidpr());
			// init formParams
			formParams.setPhone(attachOtherRegions.getPhone());
			formParams.setEmail(attachOtherRegions.getEmail());
			formParams.setLpuId(attachedLpu.getId());
			formParams.setLpuUnit(attachOtherRegions.getLpuUnit());
			formParams.setDoctorSnils(attachOtherRegions.getDoctorsnils());
			formParams.setExpDate(LocalDate.now().toString());
			formParams.setDudlNum(attachOtherRegions.getDudlNum());
			formParams.setDudlType(attachOtherRegions.getDudlType());
			formParams.setEffDate(attachOtherRegions.getEffDate().toString());
		} else {
			addressService.setGarLevels(gar);
		}

		Collection<MedMz> doctors = new ArrayList<MedMz>();
		if (formParams.getLpuId() != null) {
			Integer parentId = lpuService.getById(formParams.getLpuId()).getParentId();
			doctors = medMzService.findByLpuId(parentId != 0 ? parentId : formParams.getLpuId());
		}
		model.addAttribute("doctors", doctors);

		model.addAttribute("addrRgStr", addressService.getAddrRgStr(gar));
		model.addAttribute("addrPrStr", addressService.getAddrPrStr(gar));

		if (unpin.isPresent()) {
			if (!DateValidator.isValid(formParams.getExpDate())) {
				bindingResult.rejectValue("expDate", null);
			} else {
				attachOtherRegionsHistService.save(attachOtherRegions);
				attachOtherRegions.setExpDate(LocalDate.parse(formParams.getExpDate()));
				attachOtherRegionsService.save(attachOtherRegions);
			}
		} else if (edit.isPresent()) {
			validator.validate(formParams, bindingResult);
			validator.validate(gar, bindingResult2);
			if (!bindingResult.hasErrors() && !bindingResult2.hasErrors()) {
				attachOtherRegionsHistService.save(attachOtherRegions);
				attachOtherRegionsService.save(attachOtherRegions, formParams, gar, user.getName());
			}
		}

		model.addAttribute("scheduledExpDate", attachOtherRegions.getExpDate() != null ? attachOtherRegions.getExpDate()
				: attachOtherRegions.getEffDate().plusDays(attachOtherRegions.getPeriod()));
		model.addAttribute("personUnpinned", attachOtherRegionsService.isUnpinned(attachOtherRegions.getExpDate()));

		return "lpu-attach-edit";
	}

	@GetMapping("/attach/search")
	public String searchAttachment(Model model, HttpSession session) {

		User user = userService.getByName(SecurityContextHolder.getContext().getAuthentication().getName());
		Lpu lpu = lpuService.getById(user.getLpuId());
		model.addAttribute("lpu", lpu);

		Collection<Lpu> lpus = new ArrayList<>();
		lpus.add(lpu);
		if (lpu.getParentId() == 0
				&& user.getRoles().stream().anyMatch(t -> t.getRoleName().contains("admin"))) {
			lpus.addAll(lpuService.findByParentId(lpu.getId()));
		}
		model.addAttribute("lpus", lpus);

		model.addAttribute("lpuUnits",
				attachOtherRegionsService
						.findByParams(false, lpuService.getIdsForCriteriaBuilder(null, lpu.getId()), null, null, null,
								null, null, null, null, null, null)
						.stream().map(t -> t.getLpuUnit()).distinct().collect(Collectors.toList()));

		AttachFormParameters formParams = new AttachFormParameters();
		formParams.setLpuId(lpu.getId());
		model.addAttribute("formParams", formParams);

		Collection<MedMz> doctors = new ArrayList<MedMz>();
		if (lpu != null) {
			Integer parentId = lpu.getParentId();
			doctors = medMzService.findByLpuId(parentId != 0 ? parentId : lpu.getId());
		}
		model.addAttribute("doctors", doctors);

		Optional<Integer> page = Optional.of(1);
		Page<AttachOtherRegions> dataPage = attachOtherRegionsService.getDataPage(formParams.getLpuId(), page);
		model.addAttribute("dataPage", dataPage);
		session.setAttribute("dataPage", dataPage);

		return "lpu-attach-search";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/attach/search")
	public String searchAttachment(Model model, @ModelAttribute("formParams") AttachFormParameters formParams,
			BindingResult bindingResult, @RequestParam("page") Optional<Integer> page,
			@RequestParam("excel") Optional<?> excel, HttpSession session) {

		User user = userService.getByName(SecurityContextHolder.getContext().getAuthentication().getName());
		Lpu lpu = lpuService.getById(user.getLpuId());
		model.addAttribute("lpu", lpu);

		Collection<Lpu> lpus = new ArrayList<>();
		lpus.add(lpu);
		if (lpu.getParentId() == 0
				&& user.getRoles().stream().anyMatch(t -> t.getRoleName().contains("admin"))) {
			lpus.addAll(lpuService.findByParentId(lpu.getId()));
		}
		model.addAttribute("lpus", lpus);

		model.addAttribute("lpuUnits",
				attachOtherRegionsService
						.findByParams(formParams.getHistorical(),
								lpuService.getIdsForCriteriaBuilder(null, formParams.getLpuId()), null, null, null,
								null, null, null, null, null, null)
						.stream().map(t -> t.getLpuUnit()).distinct().collect(Collectors.toList()));

		Collection<MedMz> doctors = new ArrayList<MedMz>();
		if (lpu != null) {
			Integer parentId = lpu.getParentId();
			doctors = medMzService.findByLpuId(parentId != 0 ? parentId : lpu.getId());
		}
		model.addAttribute("doctors", doctors);

		attachOtherRegionsService.validate(formParams, bindingResult);
		Page<AttachOtherRegions> dataPage;
		if (bindingResult.hasErrors() || page.isPresent()) {
			dataPage = (Page<AttachOtherRegions>) session.getAttribute("dataPage");
		} else {
			if (excel.isPresent()) {
				return "forward:/lpu/attach/search/excel";
			}
			dataPage = attachOtherRegionsService.getDataPage(formParams, page);
			session.setAttribute("dataPage", dataPage);
		}
		model.addAttribute("dataPage", dataPage);

		return "lpu-attach-search";
	}

	@PostMapping("/attach/search/excel")
	@ResponseBody
	public ResponseEntity<?> toExcel(Model model, @ModelAttribute("formParams") AttachFormParameters formParams,
			@RequestParam("historical") Optional<?> historical) {
		ResponseEntity<?> resource;
		try {
			resource = ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=" + LocalDateTime.now().toString() + ".xlsx")
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.body(new InputStreamResource(excelService.createExcel(formParams)));
		} catch (IOException | ExcelGeneratorException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resource;
	}

	@PostMapping("/attach/word")
	@ResponseBody
	public ResponseEntity<?> toWord(Model model, @ModelAttribute("formParams") AttachFormParameters formParams,
			@ModelAttribute("gar") Gar gar, @RequestParam("rid") Long rid, HttpSession session) {
		ResponseEntity<?> resource;
		try {
			User user = userService.getByName(SecurityContextHolder.getContext().getAuthentication().getName());
			addressService.setGarLevels(gar);
			Person person = personService.findByRid(rid).stream().findAny().get();
			Policy policy = policyService.findByRid(rid).stream().max(Comparator.comparing(Policy::getPcyDateB)).get();
			Dudl dudl = dudlService.findByRid(rid).stream().filter(t -> t.getDudlNum().equals(formParams.getDudlNum()))
					.findAny().orElse(null);
			Lpu lpu = lpuService.getById(formParams.getLpuId());
			String addrRgStr = addressService.getAddrRgStr(gar);
			String addrPrStr = addressService.getAddrPrStr(gar);
			String addrMoStr = addressService.getAddrMoStr(gar);
			LocalDate addrDateB = addressService.findAddressDateB(gar, rid);
			MedMz doctor = medMzService.getById(new MedMzPk(
					lpu.getParentId().intValue() == 0 ? lpu.getId() : lpu.getParentId(), formParams.getDoctorSnils()));

			session.setAttribute("worddoc",
					IOUtils.toByteArray(
							new InputStreamResource(wordService.createDoc(user, formParams, addrRgStr, addrPrStr,
									addrMoStr, addrDateB, person, policy, dudl, lpu, doctor)).getInputStream()));

			resource = ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=" + (policy.getEnp() != null ? policy.getEnp() : policy.getPcyNum())
									+ ".docx")
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
					.body(session.getAttribute("worddoc"));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resource;
	}
	
	@GetMapping(value = "/help", produces = { "application/octet-stream" })
	public ResponseEntity<?> help() {
		try {
			ClassPathResource resource = new ClassPathResource("files/manual.pdf", this.getClass().getClassLoader());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentLength(resource.contentLength());
			headers.setContentType(MediaType.parseMediaType(("application/pdf")));
			headers.setContentDisposition(ContentDisposition.inline().filename("SPRAVKA").build());

			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
