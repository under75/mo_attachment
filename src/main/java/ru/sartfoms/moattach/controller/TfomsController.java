package ru.sartfoms.moattach.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.sartfoms.moattach.entity.AttachOtherRegions;
import ru.sartfoms.moattach.entity.Lpu;
import ru.sartfoms.moattach.exception.ExcelGeneratorException;
import ru.sartfoms.moattach.model.SearchFormParameters;
import ru.sartfoms.moattach.service.AttachOtherRegionsService;
import ru.sartfoms.moattach.service.ExcelService;
import ru.sartfoms.moattach.service.LpuService;
import ru.sartfoms.moattach.service.MedMzService;
import ru.sartfoms.moattach.util.Info;

@Controller
@RequestMapping("/tfoms")
public class TfomsController {
	private final LpuService lpuService;
	private final AttachOtherRegionsService attachOtherRegionsService;
	private final MedMzService medMzService;
	private final ExcelService excelService;
	@Autowired
	Info info;

	public TfomsController(LpuService lpuService, AttachOtherRegionsService attachOtherRegionsService,
			MedMzService medMzService, ExcelService excelService) {
		this.lpuService = lpuService;
		this.attachOtherRegionsService = attachOtherRegionsService;
		this.medMzService = medMzService;
		this.excelService = excelService;
	}
	
	@ModelAttribute
	public void addInfoToModel(Model model) {
		model.addAttribute("info", info);
	}

	@GetMapping("/attach/search")
	public String searchAttachment(Model model, HttpSession session) {
		SearchFormParameters formParams = new SearchFormParameters();
		model.addAttribute("formParams", formParams);

		Collection<Lpu> mos = lpuService.findByParentId(0);
		model.addAttribute("mos", mos);
		session.setAttribute("dataPage", null);

		return "tfoms-attach-search";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/attach/search")
	public String searchAttachment(Model model, @ModelAttribute("formParams") SearchFormParameters formParams,
			BindingResult bindingResult, @RequestParam("page") Optional<Integer> page,
			@RequestParam("excel") Optional<?> excel, HttpSession session) {

		Collection<Lpu> mos = lpuService.findByParentId(0);
		model.addAttribute("mos", mos);

		if (formParams.getMoId() != null) {
			model.addAttribute("lpus", lpuService.findByParentId(formParams.getMoId()));
		}

		model.addAttribute("lpuUnits",
				attachOtherRegionsService
						.findByParams(formParams.getHistorical(),
								lpuService.getIdsForCriteriaBuilder(formParams.getMoId(), formParams.getLpuId()), null,
								null, null, null, null, null, null, null, null)
						.stream().map(t -> t.getLpuUnit()).distinct().collect(Collectors.toList()));

		model.addAttribute("doctors", medMzService.findByLpuId(formParams.getMoId()));

		attachOtherRegionsService.validate(formParams, bindingResult);
		Page<AttachOtherRegions> dataPage;
		if (bindingResult.hasErrors() || page.isPresent()) {
			dataPage = (Page<AttachOtherRegions>) session.getAttribute("dataPage");
		} else {
			if (excel.isPresent()) {
				return "forward:/tfoms/attach/search/excel";
			}
			dataPage = attachOtherRegionsService.getDataPage(formParams, page);
			session.setAttribute("dataPage", dataPage);
		}
		model.addAttribute("dataPage", dataPage);

		return "tfoms-attach-search";
	}

	@PostMapping("/attach/search/excel")
	@ResponseBody
	public ResponseEntity<?> toExcel(Model model, @ModelAttribute("formParams") SearchFormParameters formParams,
			@RequestParam("historical") Optional<?> historical) {
		ResponseEntity<?> resource;
		try {
			resource = ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ LocalDateTime.now().toString() +".xlsx")
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.body(new InputStreamResource(excelService.createExcel(formParams)));
		} catch (IOException | ExcelGeneratorException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resource;
	}

	@GetMapping("/attach/search/word")
	@ResponseBody
	public ResponseEntity<?> toWord(@RequestParam("attachId") Optional<Long> attachId) {
		ResponseEntity<?> resource;
		try {
			AttachOtherRegions attachOtherRegions = attachOtherRegionsService.getReferenceById(attachId);
			resource = ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=" + attachOtherRegions.getPcyNum() + ".docx")
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
					.body(attachOtherRegions.getWorddoc());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resource;
	}
}
