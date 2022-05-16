package ru.learnup.bookStore.controller;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookStore.dto.BookDTO;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.service.implementation.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    BookServiceImpl bookServiceImpl;
    ModelMapper modelMapper;

    public BookController(BookServiceImpl bookServiceImpl, ModelMapper modelMapper) {
        this.bookServiceImpl = bookServiceImpl;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    public List<BookDTO.Response.Public> getAllBooks() {
        List<Book> books = bookServiceImpl.getAllBooks();
        List<BookDTO.Response.Public> mappedBooks = new ArrayList<>(books.size());
        for (Book book :
                books) {
            mappedBooks.add(modelMapper.map(book, BookDTO.Response.Public.class));
        }
        return mappedBooks;
    }

    @GetMapping("/{bookId}")
    @ResponseBody
    public BookDTO.Response.Public getBookById(@PathVariable Long bookId) {
        Book book = bookServiceImpl.getById(bookId);
        return modelMapper.map(book, BookDTO.Response.Public.class);
    }

    @PostMapping
    @ResponseBody
    public BookDTO.Response.Public createBook(BookDTO.Request.Public newBook) {
        Book book = modelMapper.map(newBook, Book.class);
        return modelMapper.map(bookServiceImpl.createBook(book), BookDTO.Response.Public.class);
    }
}
