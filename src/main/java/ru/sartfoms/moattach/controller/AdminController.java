package ru.sartfoms.moattach.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.sartfoms.moattach.entity.User;
import ru.sartfoms.moattach.model.UserDTO;
import ru.sartfoms.moattach.service.UserService;
import ru.sartfoms.moattach.util.ActiveUserStore;
import ru.sartfoms.moattach.util.Info;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	ActiveUserStore activeUserStore;
	@Autowired
	Info info;

	private final UserService userService;

	public AdminController(UserService userService) {
		this.userService = userService;
	}

	@ModelAttribute
	public void addUsersAndInfoToModel(Model model) {
		Collection<UserDTO> users = activeUserStore.getUsers();
		users.forEach(t -> {
			if (!t.getId().equals("admin")) {
				User entity = userService.getByName(t.getId());
				t.setLastname(entity.getLastname());
				t.setLpuId(entity.getLpuId());
				t.setRoles(entity.getRoles().stream().map(e->e.getRoleName()).collect(Collectors.toList()));
			}
		});
		model.addAttribute("users", users);

	}

	@GetMapping
	public String getLoggedUsers() {
		return "admin";
	}

	@GetMapping("/info")
	public String getInfo(Model model) {
		model.addAttribute("info", info);

		return "admin-info";
	}

	@PostMapping("/info")
	public String setInfo(@ModelAttribute("info") Info attr) {
		info.setMessage(attr.getMessage());
		info.setType(attr.getType());

		return "admin-info";
	}
}
