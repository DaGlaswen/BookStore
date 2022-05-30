package ru.learnup.bookStore;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.learnup.bookStore.dto.BookDTO;
import ru.learnup.bookStore.repository.AuthorRepository;
import ru.learnup.bookStore.repository.BookRepository;

@SpringBootApplication
public class LearnUpBookStoreApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LearnUpBookStoreApp.class, args);
    }

}
