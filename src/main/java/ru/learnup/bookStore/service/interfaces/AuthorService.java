package ru.learnup.bookStore.service.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.entity.BookOrder;

import java.util.List;

public interface AuthorService {

    Author createAuthor(Author author);
    Author updateAuthor(Author author);
    Boolean deleteAuthor(Long id);
    Author getById(Long id);
    List<Author> getAuthors(Pageable pageable);
    List<Author> getAllBySpec(Specification<Author> spec);
}
