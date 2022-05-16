package ru.learnup.bookStore.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false)
    @NotBlank
    private String title;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "authorId")
    )
    private Set<Author> authors = new HashSet<>();

    public void addAuthor(Author author) {
        if (authors == null) authors = new HashSet<>();
        authors.add(author);
    }

    @OneToOne(mappedBy = "book")
    private BooksWarehouse warehouse;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetailsList;

    @Column(nullable = false)
    @Positive
    private Integer yearPublished;

    @Column(nullable = false)
    @Min(value = 0)
    private Integer numberOfPages;

    @Column(nullable = false)
    @Min(value = 0)
    private Integer price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return bookId != null && Objects.equals(bookId, book.bookId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Book{" +
                "authorId=" + bookId +
                ", title='" + title + '\'' +
                ", yearPublished=" + yearPublished +
                ", numberOfPages=" + numberOfPages +
                ", price=" + price +
                '}';
    }
}
