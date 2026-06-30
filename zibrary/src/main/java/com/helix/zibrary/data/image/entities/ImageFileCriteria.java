package com.helix.zibrary.data.image.entities;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ImageFileCriteria {

    public static Specification<ImageFile> getLatestName(String param) {
        return (Root<ImageFile> root, jakarta.persistence.criteria.CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (!StringUtils.isEmpty(param)) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("fileName")), "%" + param.toLowerCase() + "%"));
            }

            return predicate;
        };
    }

}
