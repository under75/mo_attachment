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

	public Page<AttachOtherRegions> getDataPage(Integer lpuId, String lpuUnit, String doctorSnils, LocalDate effDateMin,
			LocalDate effDateMax, PageRequest page) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AttachOtherRegions> criteriaQuery = criteriaBuilder.createQuery(AttachOtherRegions.class);
		Root<AttachOtherRegions> root = criteriaQuery.from(AttachOtherRegions.class);

		Collection<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(criteriaBuilder.equal(root.get("lpuId"), lpuId));
		predicates.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("expDate")),
				criteriaBuilder.greaterThan(root.get("expDate"), LocalDate.now())));
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

		criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {}));
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("effDate")));

		TypedQuery<AttachOtherRegions> query = entityManager.createQuery(criteriaQuery);
		int totalRows = query.getResultList().size();
		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());

		return new PageImpl<AttachOtherRegions>(query.getResultList(), page, totalRows);
	}

	public Collection<AttachOtherRegions> findByParams(Integer lpuId, String lpuUnit, String doctorSnils, LocalDate effDateMin,
			LocalDate effDateMax) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AttachOtherRegions> criteriaQuery = criteriaBuilder.createQuery(AttachOtherRegions.class);
		Root<AttachOtherRegions> root = criteriaQuery.from(AttachOtherRegions.class);

		Collection<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(criteriaBuilder.equal(root.get("lpuId"), lpuId));
		predicates.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("expDate")),
				criteriaBuilder.greaterThan(root.get("expDate"), LocalDate.now())));
		if (lpuUnit != null && !lpuUnit.isEmpty()) {
			predicates.add(criteriaBuilder.equal(root.get("lpuUnit"), lpuUnit));
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

		criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {}));
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("effDate")));
		
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	public Page<AttachOtherRegions> getDataPage(Integer moId, Integer lpuId, String lpuUnit, String doctorSnils,
			LocalDate effDateMin, LocalDate effDateMax, String lastName, String firstName, String patronymic,
			LocalDate birthDay, String policyNum, PageRequest page) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AttachOtherRegions> criteriaQuery = criteriaBuilder.createQuery(AttachOtherRegions.class);
		Root<AttachOtherRegions> root = criteriaQuery.from(AttachOtherRegions.class);

		Collection<Predicate> predicates = new ArrayList<Predicate>();
		if (lpuId != null) {
			predicates.add(criteriaBuilder.equal(root.get("lpuId"), lpuId));
		} else if (moId != null){
			predicates.add(criteriaBuilder.equal(root.get("lpuId"), moId));
		}
		predicates.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("expDate")),
				criteriaBuilder.greaterThan(root.get("expDate"), LocalDate.now())));
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
			predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("patronymic")), patronymic.toUpperCase()));
		}
		if (birthDay != null) {
			predicates.add(criteriaBuilder.equal(root.get("birthDay"), birthDay));
		}
		if (policyNum != null && !policyNum.isEmpty()) {
			predicates.add(criteriaBuilder.like(root.get("pcyNum"), policyNum));
		}
		
		criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {}));
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("effDate")));
		
		TypedQuery<AttachOtherRegions> query = entityManager.createQuery(criteriaQuery);
		int totalRows = query.getResultList().size();
		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		
		return new PageImpl<AttachOtherRegions>(query.getResultList(), page, totalRows);
	}
}
