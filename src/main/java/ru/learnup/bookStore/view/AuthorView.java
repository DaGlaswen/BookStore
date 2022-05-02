package ru.learnup.bookStore.view;

import lombok.Data;
import ru.learnup.bookStore.entity.Book;

import java.util.List;

@Data
public class AuthorView {

    private Long authorId;
    private String firstName;
    private String lastName;
    private List<Book> books;
}
