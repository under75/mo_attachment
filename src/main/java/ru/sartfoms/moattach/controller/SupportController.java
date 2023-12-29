package ru.sartfoms.moattach.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import ru.sartfoms.moattach.entity.User;
import ru.sartfoms.moattach.model.MailFormParameters;
import ru.sartfoms.moattach.service.EmailService;
import ru.sartfoms.moattach.service.UserService;
import ru.sartfoms.moattach.util.Info;

@Controller
@RequestMapping("/support")
public class SupportController {
	private final EmailService emailService;
	private final UserService userService;
	private final static String MAIL_TO = "pylaev@sartfoms.ru";
	@Autowired
	Info info;

	public SupportController(EmailService emailService, UserService userService) {
		this.emailService = emailService;
		this.userService = userService;
	}

	@ModelAttribute
	public void addInfoToModel(Model model) {
		model.addAttribute("info", info);
	}

	@GetMapping("/mail")
	public String mailForm(@ModelAttribute("formParams") MailFormParameters formParameters) {

		return "support-mail.html";
	}

	@PostMapping("/mail")
	public String send(@ModelAttribute("formParams") @Valid MailFormParameters formParameters,
			BindingResult bindingResult) throws IOException, MessagingException {
		if (!bindingResult.hasErrors()) {
			User user = userService.getByName(SecurityContextHolder.getContext().getAuthentication().getName());
			if (formParameters.getAttachment().getOriginalFilename().isEmpty())
				emailService.sendMessage(MAIL_TO, formParameters.getFrom(), formParameters.getSubject(), formParameters.getBody(), user);
			else
				emailService.sendMessageWithAttachment(MAIL_TO, formParameters.getFrom(), formParameters.getSubject(), formParameters.getBody(),
						formParameters.getAttachment(), user);

			formParameters.setSuccess(true);
		}

		return "support-mail.html";
	}
}
