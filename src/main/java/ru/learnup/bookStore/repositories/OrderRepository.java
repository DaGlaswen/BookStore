package ru.learnup.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.learnup.bookStore.entities.BookOrder;

public interface OrderRepository extends JpaRepository<BookOrder, Long> {
}
