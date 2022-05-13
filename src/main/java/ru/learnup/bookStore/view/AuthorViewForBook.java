package ru.learnup.bookStore.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorViewForBook {

    private Long authorId;

    private String firstName;
    private String lastName;
}
