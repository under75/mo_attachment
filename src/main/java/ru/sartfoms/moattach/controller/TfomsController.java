package ru.sartfoms.moattach.controller;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.sartfoms.moattach.entity.AttachOtherRegions;
import ru.sartfoms.moattach.entity.Lpu;
import ru.sartfoms.moattach.model.SearchFormParameters;
import ru.sartfoms.moattach.service.AttachOtherRegionsService;
import ru.sartfoms.moattach.service.LpuService;
import ru.sartfoms.moattach.service.MedMzService;

@Controller
public class TfomsController {
	private final LpuService lpuService;
	private final AttachOtherRegionsService attachOtherRegionsService;
	private final MedMzService medMzService;

	public TfomsController(LpuService lpuService, AttachOtherRegionsService attachOtherRegionsService,
			MedMzService medMzService) {
		this.lpuService = lpuService;
		this.attachOtherRegionsService = attachOtherRegionsService;
		this.medMzService = medMzService;
	}

	@GetMapping("/tfoms/attach/search")
	public String searchAttachment(Model model, HttpSession session) {
		SearchFormParameters formParams = new SearchFormParameters();
		model.addAttribute("formParams", formParams);

		Collection<Lpu> mos = lpuService.findByParentId(0);
		model.addAttribute("mos", mos);
		session.setAttribute("dataPage", null);

		return "tfoms-attach-search";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/tfoms/attach/search")
	public String searchAttachment(Model model, @ModelAttribute("formParams") SearchFormParameters formParams,
			BindingResult bindingResult, @RequestParam("page") Optional<Integer> page,
			@RequestParam("excel") Optional<?> excel, HttpSession session) {

		Collection<Lpu> mos = lpuService.findByParentId(0);
		model.addAttribute("mos", mos);

		if (formParams.getMoId() != null) {
			model.addAttribute("lpus", lpuService.findByParentId(formParams.getMoId()));
		}

		if (formParams.getLpuId() != null) {
			model.addAttribute("lpuUnits",
					attachOtherRegionsService.findByParams(formParams.getLpuId(), null, null, null, null).stream()
							.map(t -> t.getLpuUnit()).distinct().collect(Collectors.toList()));
			Integer parentId = lpuService.getById(formParams.getLpuId()).getParentId();
			model.addAttribute("doctors", medMzService.findByLpuId(parentId != 0 ? parentId : formParams.getLpuId()));
		} else if (formParams.getMoId() != null) {
			model.addAttribute("lpuUnits",
					attachOtherRegionsService.findByParams(formParams.getMoId(), null, null, null, null).stream()
							.map(t -> t.getLpuUnit()).distinct().collect(Collectors.toList()));
			Integer parentId = lpuService.getById(formParams.getMoId()).getParentId();
			model.addAttribute("doctors", medMzService.findByLpuId(parentId != 0 ? parentId : formParams.getMoId()));
		}

		attachOtherRegionsService.validate(formParams, bindingResult);
		Page<AttachOtherRegions> dataPage;
		if (bindingResult.hasErrors() || page.isPresent()) {
			dataPage = (Page<AttachOtherRegions>) session.getAttribute("dataPage");
		} else {
			if (excel.isPresent()) {
				return "forward:/tfoms/attach/excel";
			}
			dataPage = attachOtherRegionsService.getDataPage(formParams, page);
			session.setAttribute("dataPage", dataPage);
		}
		model.addAttribute("dataPage", dataPage);

		return "tfoms-attach-search";
	}
}
