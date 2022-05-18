package ru.learnup.bookStore.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.learnup.bookStore.entity.Book;

import java.util.List;

public interface BookService {

    Book createBook(Book book);
    Book updateBook(Book book);
    Boolean deleteBook(Long id);
    Book getById(Long id);
    List<Book> getBooks(Pageable pageable);


}
