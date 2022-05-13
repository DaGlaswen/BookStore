package ru.learnup.bookStore.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookOrderId;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "bookOrder", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetailsList;

    @Min(value = 0)
    @Column(nullable = false)
    private Integer totalCheckAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookOrder bookOrder = (BookOrder) o;
        return bookOrderId != null && Objects.equals(bookOrderId, bookOrder.bookOrderId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "authorId = " + bookOrderId + ")";
    }
}
