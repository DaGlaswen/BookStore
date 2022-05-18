package ru.learnup.bookStore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;
import ru.learnup.bookStore.dto.BookDTO;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.service.implementation.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("books")
@Tag(name="BookController", description="Контроллирует проводить CRUD операции с энтити Book")
public class BookController {

    BookServiceImpl bookServiceImpl;
    ModelMapper modelMapper;

    public BookController(BookServiceImpl bookServiceImpl, ModelMapper modelMapper) {
        this.bookServiceImpl = bookServiceImpl;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(
            summary = "Get запрос книг",
            description = "Get запрос книг с реквест параметрами page, size, sort. DefaultValue у pageable: page=0," +
                    "size=5, sort=title"
    )
    public ResponseEntity<List<BookDTO.Response.Public>> getBooks(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "5") int size,
                                                                  @RequestParam(value = "sort", defaultValue = "title") String sort,
                                                                  Pageable pageable) {
        List<Book> books = bookServiceImpl.getBooks(pageable);
        List<BookDTO.Response.Public> mappedBooks = new ArrayList<>(books.size());
        for (Book book :
                books) {
            mappedBooks.add(modelMapper.map(book, BookDTO.Response.Public.class));
        }
        return new ResponseEntity<>(mappedBooks, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO.Response.Public> getBookById(@PathVariable Long bookId) {
        Book book = bookServiceImpl.getById(bookId);
        return new ResponseEntity<>(modelMapper.map(book, BookDTO.Response.Public.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDTO.Response.Public> createBook(@RequestBody BookDTO.Request.Public newBook) {
        Book book = modelMapper.map(newBook, Book.class);
        return new ResponseEntity<>(modelMapper.map(bookServiceImpl.createBook(book), BookDTO.Response.Public.class),
                HttpStatus.CREATED);
    }

//    PutMapping

}
