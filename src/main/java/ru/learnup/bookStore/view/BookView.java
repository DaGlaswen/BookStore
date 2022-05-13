package ru.learnup.bookStore.view;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookView {

    private Long bookId;
    private String title;
    private Integer numberOfPages;
    private Integer yearPublished;
    private Integer price;
    private List<AuthorViewForBook> author;
}
