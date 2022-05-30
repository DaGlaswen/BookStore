package ru.learnup.bookStore.service.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    Boolean deleteCustomer(Long id);
    Customer getById(Long id);
    List<Customer> getCustomers(Pageable pageable);
    List<Customer> getAllBySpec(Specification<Customer> spec);
}
