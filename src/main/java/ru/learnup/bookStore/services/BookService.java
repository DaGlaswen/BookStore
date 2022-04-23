package ru.learnup.bookStore.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.bookStore.entities.Author;
import ru.learnup.bookStore.entities.Book;
import ru.learnup.bookStore.repositories.AuthorRepository;
import ru.learnup.bookStore.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class BookService {

    BookRepository bookRepository;
    AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
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
