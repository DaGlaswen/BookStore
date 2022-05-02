package ru.learnup.bookStore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.repository.AuthorRepository;
import ru.learnup.bookStore.repository.BookRepository;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;

@Slf4j
@Service
public class BookService {

    BookRepository bookRepository;
    AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
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

    public Boolean delete(Long id) {
        bookRepository.delete(bookRepository.getById(id));
        return true;
    }

//    public void testingMethod() {
//        Book harry = Book.builder().numberOfPages(69).price(45).title("Harry Potter").yearPublished(1999).build();
//        Author jordan = Author.builder().firstName("Jordan").lastName("Peterson")
//                .books(List.of(harry)).build();
//        authorRepository.save(jordan);
//        bookRepository.save(harry);
//        Author jorda = authorRepository.findFirstByFirstNameAndLastName("Jordan", "Peterson");
//    }
}
