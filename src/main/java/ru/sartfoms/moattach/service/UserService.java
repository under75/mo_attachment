package ru.sartfoms.moattach.service;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.User;
import ru.sartfoms.moattach.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public User getByName(String name) {
		return repository.getReferenceById(name);
	}
}
