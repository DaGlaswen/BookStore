package ru.learnup.bookStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.learnup.bookStore.entities.Author;
import ru.learnup.bookStore.entities.Book;
import ru.learnup.bookStore.repositories.AuthorRepository;
import ru.learnup.bookStore.repositories.BookRepository;
import ru.learnup.bookStore.services.BookService;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LearnUpBookStoreApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LearnUpBookStoreApp.class, args);

        BookService bookService = context.getBean(BookService.class);
//        bookService.testingMethod();

        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
        BookRepository bookRepository = context.getBean(BookRepository.class);
        Author jordan = Author.builder().firstName("Jordan").lastName("Peterson").books(new ArrayList<>()).build();
        authorRepository.save(jordan);
        Book harryPotter = Book.builder().numberOfPages(69).price(45).title("Harry Potter").yearPublished(1999).authors(new ArrayList<>()).build();
        bookRepository.save(harryPotter);
////        jordan.getBooks().add(harryPotter);
////        authorRepository.save(jordan);
//
//
//        Author jorda = authorRepository.findFirstByFirstNameAndLastName("Jordan", "Peterson");
//        List<Book> books = jorda.getBooks();
//        System.out.println(books);
////        List<Author> authors = harryPotter.getAuthors();
////        jordan.getBooks().add(harryPotter);
//
//
//
//        bookRepository.findBooksByAuthor("Jordan", "Peterson");

//        bookRepository.save(new Book());
//        bookRepository.findBooksByAuthor("George", "Peterson");
    }

}
