package ru.sartfoms.moattach.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.sartfoms.moattach.entity.Address;
import ru.sartfoms.moattach.entity.Contact;
import ru.sartfoms.moattach.entity.Dudl;
import ru.sartfoms.moattach.entity.Lpu;
import ru.sartfoms.moattach.entity.MPIError;
import ru.sartfoms.moattach.entity.MedMz;
import ru.sartfoms.moattach.entity.Person;
import ru.sartfoms.moattach.entity.PersonData;
import ru.sartfoms.moattach.entity.Policy;
import ru.sartfoms.moattach.entity.User;
import ru.sartfoms.moattach.model.AttachFormParameters;
import ru.sartfoms.moattach.model.FerzlSearchParameters;
import ru.sartfoms.moattach.model.Gar;
import ru.sartfoms.moattach.model.PolicyTypes;
import ru.sartfoms.moattach.service.AddressService;
import ru.sartfoms.moattach.service.AttachOtherRegionsService;
import ru.sartfoms.moattach.service.AttachService;
import ru.sartfoms.moattach.service.ContactService;
import ru.sartfoms.moattach.service.DudlService;
import ru.sartfoms.moattach.service.DudlTypeService;
import ru.sartfoms.moattach.service.LpuService;
import ru.sartfoms.moattach.service.MPIErrorService;
import ru.sartfoms.moattach.service.MedMzService;
import ru.sartfoms.moattach.service.PersonDataService;
import ru.sartfoms.moattach.service.PersonService;
import ru.sartfoms.moattach.service.PolicyService;
import ru.sartfoms.moattach.service.UserService;

@Controller
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
	@Autowired
	SmartValidator validator;

	public LpuController(UserService userService, LpuService lpuService, DudlTypeService dudlTypeService,
			PersonDataService personDataService, MPIErrorService mpiErrorService, PersonService personService,
			PolicyService policyService, DudlService dudlService, AddressService addressService,
			AttachService attachService, ContactService contactService, MedMzService medMzService, AttachOtherRegionsService attachOtherRegionsService) {
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
	}

	@GetMapping("/lpu/ferzl")
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

	@PostMapping("/lpu/ferzl")
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

	@PostMapping("/lpu/attach")
	public String attachPersonToLpu(Model model, @ModelAttribute("gar") Gar gar, BindingResult bindingResult2, @RequestParam("rid") Long rid,
			@ModelAttribute("formParams") AttachFormParameters formParams, BindingResult bindingResult,
			@RequestParam("rgaddr") Optional<Integer> rgaddr, @RequestParam("cntnr") Optional<Integer> cntnr,
			@RequestParam("nrdudl") Optional<Integer> nrdudl, @RequestParam("save") Optional<String> save) {
		model.addAttribute("rid", rid);
		User user = userService.getByName(SecurityContextHolder.getContext().getAuthentication().getName());
		Lpu lpu = lpuService.getById(user.getLpuId());
		model.addAttribute("lpu", lpu);
		Collection<Lpu> lpus = new ArrayList<>();
		lpus.add(lpu);
		if (lpu.getParentId() == 0
				&& user.getRoles().stream().filter(t -> t.getRoleName().contains("admin")).count() > 0) {
			lpus.addAll(lpuService.findByParentId(lpu.getId()));
		}
		model.addAttribute("lpus", lpus);

		Collection<MPIError> errors = mpiErrorService.findAllByRid(rid);
		if (errors.size() > 0) {
			model.addAttribute("errors", errors);
			return "mpi-err";
		}
		Person person = personService.findAllByRid(rid).stream().findAny().get();
		model.addAttribute("person", person);
		Policy policy = policyService.findByRid(rid).stream().max(Comparator.comparing(Policy::getPcyDateB)).get();
		model.addAttribute("policy", policy);
		model.addAttribute("policyTypes", PolicyTypes.getInstance());
		model.addAttribute("attach", attachService.findByRid(rid).stream().findAny().orElse(null));
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
			Dudl dudl = dudls.stream().filter(t->t.getNr().intValue() == nrdudl.get().intValue()).findFirst().get();
			formParams.setDudlType(dudl.getDudlType().getDocCode());
			formParams.setDudlSer(dudl.getDudlSer());
			formParams.setDudlNum(dudl.getDudlNum());
		}

		Collection<Address> rgAddresses = addressService.findAllByRidAndAddressType(rid, AddressService.TYPE_RG);
		model.addAttribute("rgAddresses", rgAddresses);

		if (rgaddr.isPresent()) {
			// initialize from FRZL by the registration address
			addressService.initGar(gar, rgAddresses, rgaddr.get());
		} else {
			// set by UI
			addressService.setGarLevels(gar);
		}

		Collection<MedMz> doctors = new ArrayList<MedMz>();
		if (formParams.getLpuId() != null) {
			Integer parentId = lpuService.getById(formParams.getLpuId()).getParentId();
			doctors = medMzService.findByLpuId(parentId != 0 ? parentId : formParams.getLpuId());
		}
		model.addAttribute("doctors", doctors);
		
		model.addAttribute("personAge", personService.getAge(person));
		
		if (save.isPresent()) {
			validator.validate(formParams, bindingResult);
			validator.validate(gar, bindingResult2);
			if ((int)model.getAttribute("personAge") < PersonService.MAX_CHILD_AGE) {
				if (formParams.getDudlPredst().isEmpty())
					bindingResult.rejectValue("dudlPredst", null);
			}
			if (!bindingResult.hasErrors() && !bindingResult2.hasErrors())
				attachOtherRegionsService.attach(user, formParams, gar, person, policy);
		}
		
		return "lpu-attach";
	}
}
