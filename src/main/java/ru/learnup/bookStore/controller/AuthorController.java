package ru.learnup.bookStore.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookStore.dto.AuthorDTO;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.service.interfaces.AuthorService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/authors")
@Tag(name="AuthorController", description="Контроллирует CRUD операции с энтити Author")
public class AuthorController {

    AuthorService authorService;
    ModelMapper modelMapper;

    public AuthorController(AuthorService authorService, ModelMapper modelMapper) {
        this.authorService = authorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<AuthorDTO.Response.Public> createAuthor(@RequestBody AuthorDTO.Request.Public author) {
        return new ResponseEntity<>(modelMapper.map(authorService.createAuthor(
                modelMapper.map(author, Author.class)), AuthorDTO.Response.Public.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO.Response.Public>> getAuthors(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "5") int size,
                                                      @RequestParam(value = "sort", defaultValue = "title") String sort,
                                                      Pageable pageable) {
        List<Author> authors = authorService.getAuthors(pageable);
        List<AuthorDTO.Response.Public> mappedAuthors = new ArrayList<>(authors.size());
        for (Author author : authors) {
            mappedAuthors.add(modelMapper.map(author, AuthorDTO.Response.Public.class));
        }
        return new ResponseEntity<>(mappedAuthors, HttpStatus.OK);
    }

    @PutMapping("/{authorId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<AuthorDTO.Response.Public> updateAuthor(@PathVariable Long authorId,
                                                                  @RequestBody AuthorDTO.Request.Public body) {
        Author author = authorService.getById(authorId);
        if (!author.getFirstName().equals(body.getFirstName())) {
            author.setFirstName(body.getFirstName());
        }
        if (!author.getLastName().equals(body.getLastName())) {
            author.setLastName(body.getFirstName());
        }
        Set<Book> bookEntities = body.getBooks().stream().map(v -> modelMapper.map(v, Book.class))
                .collect(Collectors.toCollection(HashSet::new));
        if (!author.getBooks().equals(bookEntities)) {
            author.setBooks(bookEntities);
        }
        Author updatedAuthor = authorService.updateAuthor(author);

        return new ResponseEntity<>(modelMapper.map(updatedAuthor, AuthorDTO.Response.Public.class), HttpStatus.OK);
    }

    @DeleteMapping("/{authorId}")
    @Secured("ROLE_ADMIN")
    public Boolean deleteAuthor(@PathVariable Long authorId) { return authorService.deleteAuthor(authorId); }
}
