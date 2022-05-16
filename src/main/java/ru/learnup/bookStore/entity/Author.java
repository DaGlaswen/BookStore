package ru.learnup.bookStore.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    @ManyToMany(mappedBy = "authors")
    @ToString.Exclude
    private Set<Book> books = new HashSet<>();

    public void addBook(Book book) {
        if (books == null) books = new HashSet<>();
        books.add(book);
    }

    @Column(nullable = false)
    @NotBlank
    private String firstName;

    @Column(nullable = false)
    @NotBlank
    private String lastName;

//    public Author(String firstName, String lastName) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "authorId = " + authorId + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Author author = (Author) o;
        return authorId != null && Objects.equals(authorId, author.authorId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
