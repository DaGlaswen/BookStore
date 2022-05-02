package ru.learnup.bookStore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookStore.service.AuthorService;

@RestController
@RequestMapping("authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorView;
}
