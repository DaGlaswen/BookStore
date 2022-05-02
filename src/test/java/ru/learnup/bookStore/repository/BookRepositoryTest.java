package ru.learnup.bookStore.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.entity.Book;

import java.util.List;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;


    @Test
    public void printBooksByAuthor() {
        Author author = Author.builder()
                .firstName("Jordan")
                .lastName("Peterson")
                .build();

        Book book = Book.builder()
                .yearPublished(1999)
                .numberOfPages(132)
                .price(99)
                .title("Beyond Order")
                .build();

        Book book1 = Book.builder()
                .title("Political Correctness")
                .price(99)
                .numberOfPages(192)
                .yearPublished(2001)
                .build();

        book.addAuthor(author);
        book1.addAuthor(author);

        bookRepository.saveAll(List.of(book, book1));

        List<Book> booksByAuthor = bookRepository.findBooksByAuthor("Jordan", "Peterson");

        System.out.println("booksByAuthor = " + booksByAuthor);
    }

}