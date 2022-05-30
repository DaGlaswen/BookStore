package ru.learnup.bookStore.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.exception.EntityNotFoundException;
import ru.learnup.bookStore.repository.AuthorRepository;
import ru.learnup.bookStore.repository.BookRepository;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookServiceImpl implements ru.learnup.bookStore.service.interfaces.BookService {

    BookRepository bookRepository;
    AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Lock(value = LockModeType.READ)
    public Book updateBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic log exception for book {}", book.getBookId());
            throw e;
        }
    }

    @Transactional
    public Boolean deleteBook(Long id) {
        bookRepository.delete(bookRepository.getById(id));
        return true;
    }

    public Book getById(Long id) throws EntityNotFoundException {
        return bookRepository.getById(id);
    }

    public List<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).stream().collect(Collectors.toList());
    }

    public List<Book> getAllBySpec(Specification<Book> spec) {
        return bookRepository.findAll(spec);
    }
}
