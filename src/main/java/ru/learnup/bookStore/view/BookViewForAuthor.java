package ru.learnup.bookStore.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookViewForAuthor {

    private Long bookId;
    private String title;
    private Integer numberOfPages;
    private Integer yearPublished;
    private Integer price;
}
