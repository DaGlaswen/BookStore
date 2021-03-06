package ru.learnup.bookStore.specification.builder;

import ru.learnup.bookStore.entity.Book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import ru.learnup.bookStore.specification.BookSpecification;
import ru.learnup.bookStore.specification.util.SearchOperation;
import ru.learnup.bookStore.specification.util.SpecSearchCriteria;

public final class BookSpecificationsBuilder {

    private final List<SpecSearchCriteria> params;

    public BookSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    // API

    public final BookSpecificationsBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final BookSpecificationsBuilder with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
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

    public Specification<Book> build() {
        if (params.size() == 0)
            return null;

        Specification<Book> result = new BookSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new BookSpecification(params.get(i)))
                    : Specification.where(result).and(new BookSpecification(params.get(i)));
        }

        return result;
    }

    public final BookSpecificationsBuilder with(BookSpecification spec) {
        params.add(spec.getCriteria());
        return this;
    }

    public final BookSpecificationsBuilder with(SpecSearchCriteria criteria) {
        params.add(criteria);
        return this;
    }
}