package ru.learnup.bookStore.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.auditing.AuditingHandler;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private String title;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "authorId")
    )
    private List<Author> authors;

    public void addAuthor(Author author) {
        if (authors == null) authors = new ArrayList<>();
        authors.add(author);
    }

    @OneToOne(mappedBy = "book")
    private BooksWarehouse warehouse;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetailsList;

    @Column(nullable = false)
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
