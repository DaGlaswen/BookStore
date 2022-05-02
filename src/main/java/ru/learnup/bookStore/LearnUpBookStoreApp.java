package ru.learnup.bookStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.learnup.bookStore.repository.AuthorRepository;
import ru.learnup.bookStore.repository.BookRepository;

@SpringBootApplication
public class LearnUpBookStoreApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LearnUpBookStoreApp.class, args);

        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
        BookRepository bookRepository = context.getBean(BookRepository.class);
    }

}
