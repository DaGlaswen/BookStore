package ru.learnup.bookStore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
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
