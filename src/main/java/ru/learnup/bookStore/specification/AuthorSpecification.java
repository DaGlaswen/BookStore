package ru.learnup.bookStore.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.filter.AuthorFilter;

import javax.persistence.criteria.Predicate;

public class AuthorSpecification {

    public static Specification<Author> byFilter(AuthorFilter filter) {

        return (root, q, cb) -> {

            Predicate predicate = cb.isNotNull(root.get("id"));

            if (filter.getFirstName() != null) {
                predicate = cb.and(predicate, cb.like(root.get("firstName"), "%" + filter.getFirstName() + "%"));
            }
            if (filter.getLastName() != null) {
                predicate = cb.and(predicate, cb.like(root.get("lastName"), "%" + filter.getFirstName() + "%"));
            }
            return predicate;
        };
    }
}
