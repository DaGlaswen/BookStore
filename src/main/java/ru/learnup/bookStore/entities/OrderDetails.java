package ru.learnup.bookStore.entities;

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
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailsId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private BookOrder bookOrderId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ordered_book_id")
    private Book book;

    @Min(value = 0)
    @Column(nullable = false)
    private Integer quantity;


    // Цена за n книг определенного bookId
    @Min(value = 0)
    @Column(nullable = false)
    private Integer totalPrice;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "authorId = " + orderDetailsId + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderDetails that = (OrderDetails) o;
        return orderDetailsId != null && Objects.equals(orderDetailsId, that.orderDetailsId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
