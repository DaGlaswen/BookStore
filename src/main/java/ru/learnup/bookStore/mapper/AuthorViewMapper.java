package ru.learnup.bookStore.mapper;

import org.springframework.stereotype.Component;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.view.AuthorView;

@Component
public class AuthorViewMapper {

    public AuthorView mapToView(Author author) {
        AuthorView view = new AuthorView();
        view.setAuthorId(author.getAuthorId());
        view.setFirstName(author.getFirstName());
        view.setLastName(author.getLastName());
        if (author.getBooks() != null) {
            view.setBooks(author.getBooks().stream().map(book -> new BookView(book.getBookId(), book.getNumberOfPages(),
                    book.getPrice(), book)));
        }
    }
}
