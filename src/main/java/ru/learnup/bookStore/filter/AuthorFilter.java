package ru.learnup.bookStore.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class AuthorFilter {

    private final String firstName;
    private final String lastName;


}
