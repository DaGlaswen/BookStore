package ru.learnup.bookStore.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookStore.dto.AuthorDTO;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.service.implementation.AuthorServiceImpl;
import ru.learnup.bookStore.service.implementation.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping
    @ResponseBody
    public List<AuthorDTO.Response.Public> getAuthors(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "5") int size,
                                                      @RequestParam(value = "sort", defaultValue = "title") String sort,
                                                      Pageable pageable) {
        List<Author> authors = authorServiceImpl.getAuthors(pageable);
        List<AuthorDTO.Response.Public> mappedAuthors = new ArrayList<>(authors.size());
        for (Author author : authors) {
            mappedAuthors.add(modelMapper.map(author, AuthorDTO.Response.Public.class));
        }
        return mappedAuthors;
    }
}
