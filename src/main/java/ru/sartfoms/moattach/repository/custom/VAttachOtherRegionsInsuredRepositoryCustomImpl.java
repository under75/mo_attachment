package ru.sartfoms.moattach.repository.custom;

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

import ru.sartfoms.moattach.entity.VAttachOtherRegionsInsured;

@Component
public class VAttachOtherRegionsInsuredRepositoryCustomImpl implements VAttachOtherRegionsInsuredRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<VAttachOtherRegionsInsured> getDataPage(Boolean historical, Collection<Integer> lpuIds, PageRequest page) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<VAttachOtherRegionsInsured> criteriaQuery = criteriaBuilder
				.createQuery(VAttachOtherRegionsInsured.class);
		Root<VAttachOtherRegionsInsured> root = criteriaQuery.from(VAttachOtherRegionsInsured.class);

		Collection<Predicate> predicates = new ArrayList<Predicate>();
		if (!historical) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("expDate"), LocalDate.now()));
		}
		if (lpuIds.size() > 0) {
			predicates.add(root.get("lpuId").in(lpuIds));
		}

		criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {}));

		TypedQuery<VAttachOtherRegionsInsured> query = entityManager.createQuery(criteriaQuery);
		int totalRows = query.getResultList().size();
		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());

		return new PageImpl<VAttachOtherRegionsInsured>(query.getResultList(), page, totalRows);
	}

}
