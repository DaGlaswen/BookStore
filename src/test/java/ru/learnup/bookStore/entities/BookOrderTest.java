package ru.learnup.bookStore.entities;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.bookStore.repositories.CustomerRepository;
import ru.learnup.bookStore.repositories.OrderRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookOrderTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void saveWithCustomer() {
        Customer customer = Customer.builder()
                .birthDate(LocalDate.parse("1999-12-12"))
                .firstName("Daniel")
                .lastName("Golovinov")
                .build();

        BookOrder bookOrder = BookOrder.builder()
                .customer(customer)
                .totalCheckAmount(199)
                .build();

        orderRepository.save(bookOrder);
    }

    @Test
    @Transactional
    public void delete() {
        orderRepository.deleteByTotalAmount(199);
    }

    @Test
    public void update() {
        BookOrder order = orderRepository.findFirstByTotalCheckAmount(199);


        Customer customer = customerRepository.findFirstByFirstName("Daniel");
        customer.setFirstName("Danil");
        customerRepository.save(customer);


//        orderRepository.updateBookOrderByTotalCheckAmountAndCustomer(order.getBookOrderId(),
//                191, customer);
    }


}