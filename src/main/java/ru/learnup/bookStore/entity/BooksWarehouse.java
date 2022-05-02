package ru.learnup.bookStore.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BooksWarehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookWarehouseId;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "bookId")
    private Book book;

    @Min(value = 0)
    @Column(nullable = false)
    private Integer booksLeftInStock;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BooksWarehouse that = (BooksWarehouse) o;
        return bookWarehouseId != null && Objects.equals(bookWarehouseId, that.bookWarehouseId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}