package ru.learnup.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.learnup.bookStore.entities.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
