package com.helix.zibrary.data.setup.entities;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

public class BranchCriteria {

    public static Specification<Branch> buildCriteria(Branch companyBranch, List<UUID> defaultUserAccess) {
        return (Root<Branch> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (defaultUserAccess != null && !defaultUserAccess.isEmpty()) {
                CriteriaBuilder.In<UUID> inClause = cb.in(root.get("id"));
                for (UUID id : defaultUserAccess) {
                    inClause.value(id);
                }
                predicate = cb.and(predicate, inClause);
            }

            if (!ObjectUtils.isEmpty(companyBranch.getName())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + companyBranch.getName().toLowerCase() + "%"));
            }

            predicate = cb.and(predicate, cb.equal(root.get("deleted"), false));

            return predicate;
        };
    }

}
