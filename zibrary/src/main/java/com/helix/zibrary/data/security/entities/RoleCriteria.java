package com.helix.zibrary.data.security.entities;

import com.helix.zibrary.enumeration.general.ActiveStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class RoleCriteria {

    public static Specification<Role> buildCriteria(Role role, ActiveStatus active) {
        return (Root<Role> root, jakarta.persistence.criteria.CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (!StringUtils.hasLength(role.getName())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + role.getName().toLowerCase() + "%"));
            }

            if(!active.equals(ActiveStatus.ALL)){
                predicate = cb.and(predicate, cb.equal(root.get("active"), active.getValue()));
            }

            return predicate;
        };
    }

}
