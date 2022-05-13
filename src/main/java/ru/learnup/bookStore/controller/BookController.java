package ru.learnup.bookStore.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.hateoas.CollectionModel;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookStore.dto.BookDTO;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.mapper.BookModelAssembler;
import ru.learnup.bookStore.service.BookService;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    BookService bookService;
    ModelMapper modelMapper;
    BookModelAssembler assembler;

    public BookController(BookService bookService, ModelMapper modelMapper, BookModelAssembler assembler) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.assembler = assembler;
    }

    @GetMapping
    @ResponseBody
    public CollectionModel<BookDTO.Response.Public> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return assembler.toCollectionModel(books);
    }

    @GetMapping("/{bookId}")
    @ResponseBody
    public BookDTO.Response.Public getBookById(@PathVariable Long bookId) {
        Book book = bookService.getById(bookId);
        return assembler.toModel(book);
    }

    @PostMapping
    @ResponseBody
    public BookDTO.Response.Public createBook(BookDTO.Request.Public newBook) {
        Book book = assembler.fromModel(newBook);
        return assembler.toModel(bookService.createBook(book));
    }
}
