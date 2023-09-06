package ru.sartfoms.moattach.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import ru.sartfoms.moattach.entity.AttachOtherRegions;

@Component
public class AttachOtherRegionsDao {
	@PersistenceContext
	private EntityManager entityManager;

	public AttachOtherRegionsDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Page<AttachOtherRegions> getDataPage(Boolean historical, Collection<Integer> lpuIds, String lpuUnit,
			String doctorSnils, LocalDate effDateMin, LocalDate effDateMax, PageRequest page) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AttachOtherRegions> criteriaQuery = criteriaBuilder.createQuery(AttachOtherRegions.class);
		Root<AttachOtherRegions> root = criteriaQuery.from(AttachOtherRegions.class);

		criteriaQuery.select(root).where(getPredicates(criteriaBuilder, root, historical, lpuIds, lpuUnit, doctorSnils,
				effDateMin, effDateMax, null, null, null, null, null).toArray(new Predicate[] {}));
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("effDate")));

		TypedQuery<AttachOtherRegions> query = entityManager.createQuery(criteriaQuery);
		int totalRows = query.getResultList().size();
		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());

		return new PageImpl<AttachOtherRegions>(query.getResultList(), page, totalRows);
	}

	public Page<AttachOtherRegions> getDataPage(Boolean historical, Collection<Integer> lpuIds, String lpuUnit,
			String doctorSnils, LocalDate effDateMin, LocalDate effDateMax, String lastName, String firstName,
			String patronymic, LocalDate birthDay, String policyNum, PageRequest page) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AttachOtherRegions> criteriaQuery = criteriaBuilder.createQuery(AttachOtherRegions.class);
		Root<AttachOtherRegions> root = criteriaQuery.from(AttachOtherRegions.class);

		criteriaQuery.select(root)
				.where(getPredicates(criteriaBuilder, root, historical, lpuIds, lpuUnit, doctorSnils, effDateMin,
						effDateMax, lastName, firstName, patronymic, birthDay, policyNum).toArray(new Predicate[] {}));
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("effDate")));

		TypedQuery<AttachOtherRegions> query = entityManager.createQuery(criteriaQuery);
		int totalRows = query.getResultList().size();
		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());

		return new PageImpl<AttachOtherRegions>(query.getResultList(), page, totalRows);
	}

	public Collection<AttachOtherRegions> toCollection(Boolean historical, Collection<Integer> lpuIds, String lpuUnit,
			String doctorSnils, LocalDate effDateMin, LocalDate effDateMax, String lastName, String firstName,
			String patronymic, LocalDate birthday, String policyNum) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AttachOtherRegions> criteriaQuery = criteriaBuilder.createQuery(AttachOtherRegions.class);
		Root<AttachOtherRegions> root = criteriaQuery.from(AttachOtherRegions.class);

		criteriaQuery.select(root)
				.where(getPredicates(criteriaBuilder, root, historical, lpuIds, lpuUnit, doctorSnils, effDateMin,
						effDateMax, lastName, firstName, patronymic, birthday, policyNum).toArray(new Predicate[] {}));
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("effDate")));

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	private Collection<Predicate> getPredicates(CriteriaBuilder criteriaBuilder, Root<AttachOtherRegions> root,
			Boolean historical, Collection<Integer> lpuIds, String lpuUnit, String doctorSnils, LocalDate effDateMin,
			LocalDate effDateMax, String lastName, String firstName, String patronymic, LocalDate birthDay,
			String policyNum) {
		Collection<Predicate> predicates = new ArrayList<Predicate>();
		if (!historical) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("expDate"), LocalDate.now()));
		}
		if (lpuIds.size() > 0) {
			predicates.add(root.get("lpuId").in(lpuIds));
		}
		if (lpuUnit != null && !lpuUnit.isEmpty()) {
			predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(root.get("lpuUnit")), lpuUnit.toUpperCase()));
		}
		if (doctorSnils != null && !doctorSnils.isEmpty()) {
			predicates.add(criteriaBuilder.equal(root.get("doctorsnils"), doctorSnils));
		}
		if (effDateMin != null && effDateMax != null) {
			predicates.add(criteriaBuilder.between(root.get("effDate"), effDateMin, effDateMax));
		} else if (effDateMin != null) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("effDate"), effDateMin));
		} else if (effDateMax != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("effDate"), effDateMax));
		}
		if (lastName != null && !lastName.isEmpty()) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("lastName")), lastName.toUpperCase()));
		}
		if (firstName != null && !firstName.isEmpty()) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("firstName")), firstName.toUpperCase()));
		}
		if (patronymic != null && !patronymic.isEmpty()) {
			predicates
					.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("patronymic")), patronymic.toUpperCase()));
		}
		if (birthDay != null) {
			predicates.add(criteriaBuilder.equal(root.get("birthDay"), birthDay));
		}
		if (policyNum != null && !policyNum.isEmpty()) {
			predicates.add(criteriaBuilder.like(root.get("pcyNum"), policyNum));
		}

		return predicates;
	}
}
