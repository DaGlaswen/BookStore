package ru.learnup.bookStore.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.learnup.bookStore.repositories.BookRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookTest {

    @Autowired
    BookRepository bookRepository;



}