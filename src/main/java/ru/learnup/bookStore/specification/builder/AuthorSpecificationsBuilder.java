package ru.learnup.bookStore.specification.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.specification.AuthorSpecification;
import ru.learnup.bookStore.specification.util.SearchOperation;
import ru.learnup.bookStore.specification.util.SpecSearchCriteria;

public final class AuthorSpecificationsBuilder {

    private final List<SpecSearchCriteria> params;

    public AuthorSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    // API

    public final AuthorSpecificationsBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final AuthorSpecificationsBuilder with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SpecSearchCriteria(orPredicate, key, op, value));
        }
        return this;
    }

    public Specification<Author> build() {
        if (params.size() == 0)
            return null;

        Specification<Author> result = new AuthorSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new AuthorSpecification(params.get(i)))
                    : Specification.where(result).and(new AuthorSpecification(params.get(i)));
        }

        return result;
    }

    public final AuthorSpecificationsBuilder with(AuthorSpecification spec) {
        params.add(spec.getCriteria());
        return this;
    }

    public final AuthorSpecificationsBuilder with(SpecSearchCriteria criteria) {
        params.add(criteria);
        return this;
    }
}
