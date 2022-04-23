package ru.learnup.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.learnup.bookStore.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
