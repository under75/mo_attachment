package ru.sartfoms.moattach.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import ru.sartfoms.moattach.entity.PersonData;
import ru.sartfoms.moattach.model.FerzlSearchParameters;
import ru.sartfoms.moattach.repository.PersonDataRepository;
import ru.sartfoms.moattach.util.DateValidator;
import static ru.sartfoms.moattach.util.Constants.*;

@Service
public class PersonDataService {
	private static final Integer PAGE_SIZE = 12;
	private final PersonDataRepository personDataRepository;

	public PersonDataService(PersonDataRepository personDataRepository) {
		this.personDataRepository = personDataRepository;
	}

	public Page<PersonData> getDataPage(FerzlSearchParameters searchParams, String userName, Optional<Integer> page) {
		int currentPage = page.orElse(1);
		Page<PersonData> dataPage;
		PageRequest pageRequest = PageRequest.of(currentPage - 1, PAGE_SIZE);
		if (searchParams.getDateFrom() != null && !searchParams.getDateFrom().isEmpty()
				&& searchParams.getDateTo() != null && !searchParams.getDateTo().isEmpty()
				&& DateValidator.isValid(searchParams.getDateFrom())
				&& DateValidator.isValid(searchParams.getDateTo())) {
			LocalDate start = LocalDate.parse(searchParams.getDateFrom());
			LocalDate end = LocalDate.parse(searchParams.getDateTo()).plusDays(1);
			dataPage = personDataRepository.findByUserAndDtInsBetweenOrderByDtInsDesc(userName, start.atStartOfDay(),
					end.atStartOfDay(), pageRequest);
		} else if (searchParams.getDateFrom() != null && !searchParams.getDateFrom().isEmpty()
				&& DateValidator.isValid(searchParams.getDateFrom())) {
			LocalDate start = LocalDate.parse(searchParams.getDateFrom());
			dataPage = personDataRepository.findByUserAndDtInsAfterOrderByDtInsDesc(userName, start.atStartOfDay(),
					pageRequest);
		} else if (searchParams.getDateTo() != null && !searchParams.getDateTo().isEmpty()
				&& DateValidator.isValid(searchParams.getDateTo())) {
			LocalDate end = LocalDate.parse(searchParams.getDateTo()).plusDays(1);
			dataPage = personDataRepository.findByUserAndDtInsBeforeOrderByDtInsDesc(userName, end.atStartOfDay(),
					pageRequest);
		} else {
			dataPage = personDataRepository.findByUserOrderByDtInsDesc(userName, pageRequest);
		}

		return dataPage;
	}

	public PersonData saveRequest(FerzlSearchParameters searchParams, String userName) {

		PersonData personData = new PersonData();
		if (!searchParams.getPolicyType().isEmpty())
			personData.setPcyType(searchParams.getPolicyType());
		if (!searchParams.getPolicySer().isEmpty())
			personData.setPcySer(searchParams.getPolicySer());
		if (!searchParams.getPolicyNum().isEmpty())
			personData.setPcy(searchParams.getPolicyNum());
		if (searchParams.getDudlType() != null)
			personData.setDudlType(searchParams.getDudlType());
		if (!searchParams.getDudlSer().isEmpty())
			personData.setDudlSer(searchParams.getDudlSer());
		if (!searchParams.getDudlNum().isEmpty())
			personData.setDudlNum(searchParams.getDudlNum());
		if (!searchParams.getFirstName().isEmpty())
			personData.setFirstName(searchParams.getFirstName());
		if (!searchParams.getLastName().isEmpty())
			personData.setLastName(searchParams.getLastName());
		if (!searchParams.getPatronymic().isEmpty())
			personData.setPatronymic(searchParams.getPatronymic());
		if (!searchParams.getBirthDay().isEmpty())
			personData.setBirthDay(LocalDate.parse(searchParams.getBirthDay()));
		personData.setUser(userName);
		personData.setDtIns(LocalDateTime.now());
		personData.setOwner(OWNER_REST);

		return personDataRepository.save(personData);
	}

	public void validate(FerzlSearchParameters searchParams, BindingResult bindingResult) {
		if (!searchParams.getBirthDay().isEmpty() && !DateValidator.isValid(searchParams.getBirthDay())) {
			bindingResult.rejectValue("birthDay", "");
		}
		if (searchParams.getPolicyNum().isEmpty()
				&& (searchParams.getDudlNum().isEmpty() || searchParams.getDudlSer().isEmpty()
						|| searchParams.getDudlType() == null || searchParams.getLastName().isEmpty()
						|| searchParams.getFirstName().isEmpty() || searchParams.getBirthDay().isEmpty())) {
			bindingResult.addError(new ObjectError("globalError","Для успешного поиска необходимо заполнить данные полиса либо данные ДУдЛ и ФИО-ДР"));
		}

	}

	public PersonData getPersonDataByRid(Long rid) {
		return personDataRepository.getReferenceById(rid);
	}

	public PersonData save(PersonData personData) {
		return personDataRepository.save(personData);
	}
}
