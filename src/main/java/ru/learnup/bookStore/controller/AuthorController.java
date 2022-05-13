package ru.learnup.bookStore.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookStore.dto.AuthorDTO;
import ru.learnup.bookStore.dto.BookDTO;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.mapper.AuthorModelAssembler;
import ru.learnup.bookStore.service.AuthorService;
import ru.learnup.bookStore.service.BookService;

@RestController
@RequestMapping("authors")
public class AuthorController {

    AuthorService authorService;
    BookService bookService;
    AuthorModelAssembler assembler;

    public AuthorController(AuthorService authorService, BookService bookService, AuthorModelAssembler assembler) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.assembler = assembler;
    }

    @PostMapping
    @ResponseBody
    public AuthorDTO.Response.Public createAuthor(AuthorDTO.Request.Public newAuthor) {
        Author author = assembler.fromModel(newAuthor);
        return assembler.toModel(authorService.createAuthor(author));
    }
}
