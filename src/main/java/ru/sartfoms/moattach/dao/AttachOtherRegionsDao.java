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

		TypedQuery<AttachOtherRegions> query = entityManager.createQuery(criteriaQuery);
		int totalRows = query.getResultList().size();
		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());

		return new PageImpl<AttachOtherRegions>(query.getResultList(), page, totalRows);
	}
}
