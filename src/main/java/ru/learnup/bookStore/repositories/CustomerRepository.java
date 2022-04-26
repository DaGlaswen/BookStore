package ru.learnup.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learnup.bookStore.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findFirstByFirstName(String firstName);
}
