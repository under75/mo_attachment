package ru.sartfoms.moattach.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;

import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.Person;
import ru.sartfoms.moattach.repository.PersonRepository;

@Service
public class PersonService {
	public static final int MAX_CHILD_AGE = 18;
	private final PersonRepository personRepository;

	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public Collection<Person> findAllByRid(Long rid) {
		return personRepository.findAllByRid(rid);
	}

	public int getAge(Person person) {
		return Period.between(person.getBirthDay(), LocalDate.now()).getYears();
	}

}
