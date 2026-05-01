package com.helix.zibrary.data.security.entities;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class RoleCriteria {

    public static Specification<Role> buildCriteria(Role role, Boolean activeStatus) {
        return (Root<Role> root, jakarta.persistence.criteria.CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (!StringUtils.isEmpty(role.getName())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + role.getName().toLowerCase() + "%"));
            }

            if(activeStatus != null){
                predicate = cb.and(predicate, cb.equal(root.get("isActive"), activeStatus));
            }

            return predicate;
        };
    }

}
