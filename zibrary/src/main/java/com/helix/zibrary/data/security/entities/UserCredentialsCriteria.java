package com.helix.zibrary.data.security.entities;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class UserCredentialsCriteria {

    public static Specification<UserCredentials> buildCriteria(UserCredentials userCredentials, Boolean activeStatus) {
        return (Root<UserCredentials> root, jakarta.persistence.criteria.CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (!StringUtils.isEmpty(userCredentials.getEmail())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("email")), "%" + userCredentials.getEmail().toLowerCase() + "%"));
            }

            if (!StringUtils.isEmpty(userCredentials.getRegisteredName())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("registeredName")), "%" + userCredentials.getRegisteredName().toLowerCase() + "%"));
            }

            if(activeStatus != null){
                predicate = cb.and(predicate, cb.equal(root.get("isActive"), activeStatus));
            }

            //Filter Deleted Account
            predicate = cb.and(predicate, cb.equal(root.get("deleted"), false));

            return predicate;
        };
    }

}
