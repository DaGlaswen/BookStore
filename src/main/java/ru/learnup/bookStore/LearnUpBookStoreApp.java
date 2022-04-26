package ru.learnup.bookStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.learnup.bookStore.entities.Author;
import ru.learnup.bookStore.entities.Book;
import ru.learnup.bookStore.repositories.AuthorRepository;
import ru.learnup.bookStore.repositories.BookRepository;
import ru.learnup.bookStore.repositories.OrderRepository;
import ru.learnup.bookStore.services.BookService;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LearnUpBookStoreApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LearnUpBookStoreApp.class, args);

        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
        BookRepository bookRepository = context.getBean(BookRepository.class);
    }

}
