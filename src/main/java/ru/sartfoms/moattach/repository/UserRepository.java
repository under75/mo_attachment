package ru.sartfoms.moattach.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.moattach.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
