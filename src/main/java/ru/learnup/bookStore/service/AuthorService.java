package ru.learnup.bookStore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.filter.AuthorFilter;
import ru.learnup.bookStore.repository.AuthorRepository;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;
import static ru.learnup.bookStore.specification.AuthorSpecification.byFilter;

@Service
@Slf4j
public class AuthorService {

    private AuthorRepository authorRepository;

    @Transactional
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorBy(AuthorFilter filter) {
        Specification<Author> specification = where(byFilter(filter));
        return authorRepository.findAll(specification);
    }

    @Transactional
    @Lock(value = LockModeType.READ)
    public Author update(Author author) {
        try {
            return authorRepository.save(author);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic log exception for author {}", author.getAuthorId());
            throw e;
        }
    }

    public Boolean delete(Long id) {
        authorRepository.delete(authorRepository.getById(id));
        return true;
    }
}
