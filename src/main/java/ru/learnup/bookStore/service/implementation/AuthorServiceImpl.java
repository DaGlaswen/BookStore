package ru.learnup.bookStore.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.repository.AuthorRepository;
import ru.learnup.bookStore.service.interfaces.AuthorService;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }


    @Override
    public List<Author> getAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable).stream().collect(Collectors.toList());
    }

    @Override
    public List<Author> getAllBySpec(Specification<Author> spec) {
        return authorRepository.findAll(spec).stream().collect(Collectors.toList());
    }


    @Transactional
    @Lock(value = LockModeType.READ)
    @Override
    public Author updateAuthor(Author author) {
        try {
            return authorRepository.save(author);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic log exception for author {}", author.getAuthorId());
            throw e;
        }
    }

    @Override
    @Transactional
    public Boolean deleteAuthor(Long id) {
        authorRepository.delete(authorRepository.getById(id));
        return true;
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.getById(id);
    }
}
