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

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    @Transactional
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

//    @Transactional
//    public Author addBookToAuthor(Author author, Book book) {
//        Optional<List<Book>> books = Optional.of(author.getBooks());
//        books.ifPresent(author.setBooks(books.get().add(book)));
//    }

    public List<Author> getAuthors(Pageable pageable) {
        return authorRepository.findAll();
    }

//    public Author getAuthorBy(AuthorFilter filter) {
//        Specification<Author> specification = where(byFilter(filter));
//        return authorRepository.findAll(specification);
//    }

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
