package ru.learnup.bookStore.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookStore.dto.AuthorDTO;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.service.implementation.AuthorServiceImpl;
import ru.learnup.bookStore.service.implementation.BookServiceImpl;

@RestController
@RequestMapping("authors")
public class AuthorController {

    AuthorServiceImpl authorServiceImpl;
    BookServiceImpl bookServiceImpl;
    ModelMapper modelMapper;

    public AuthorController(AuthorServiceImpl authorServiceImpl, BookServiceImpl bookServiceImpl, ModelMapper modelMapper) {
        this.authorServiceImpl = authorServiceImpl;
        this.bookServiceImpl = bookServiceImpl;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseBody
    public AuthorDTO.Response.Public createAuthor(AuthorDTO.Request.Public newAuthor) {
        Author author = modelMapper.map(newAuthor, Author.class);
        return modelMapper.map(authorServiceImpl.createAuthor(author), AuthorDTO.Response.Public.class);
    }
}
