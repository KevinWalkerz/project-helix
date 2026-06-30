package com.helix.zibrary.data.security.entities;

import com.helix.zibrary.enumeration.general.ActiveStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class UserCredentialsCriteria {

    public static Specification<UserCredentials> buildCriteria(UserCredentials userCredentials, ActiveStatus active) {
        return (Root<UserCredentials> root, jakarta.persistence.criteria.CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (!StringUtils.hasLength(userCredentials.getUsername())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("username")), "%" + userCredentials.getUsername().toLowerCase() + "%"));
            }

            if (!StringUtils.hasLength(userCredentials.getEmail())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("email")), "%" + userCredentials.getEmail().toLowerCase() + "%"));
            }

            if (!StringUtils.hasLength(userCredentials.getRegisteredName())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("registeredName")), "%" + userCredentials.getRegisteredName().toLowerCase() + "%"));
            }

            if(!active.equals(ActiveStatus.ALL)){
                predicate = cb.and(predicate, cb.equal(root.get("active"), active.getValue()));
            }

            return predicate;
        };
    }

}
