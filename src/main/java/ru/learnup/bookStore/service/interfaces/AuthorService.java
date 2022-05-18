package ru.learnup.bookStore.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.filter.AuthorFilter;

import java.util.List;

public interface AuthorService {

    Author createAuthor(Author author);
    List<Author> getAuthors(Pageable pageable);
    Author getAuthorBy(AuthorFilter filter);
    Author update(Author author);
}
