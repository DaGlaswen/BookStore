package ru.learnup.bookStore.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.learnup.bookStore.repository.BookRepository;

@SpringBootTest
class BookTest {

    @Autowired
    BookRepository bookRepository;



}