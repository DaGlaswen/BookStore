package ru.learnup.bookStore.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookStore.dto.BookOrderDTO;
import ru.learnup.bookStore.entity.BookOrder;
import ru.learnup.bookStore.service.interfaces.BookOrderService;
import ru.learnup.bookStore.specification.BookOrderSpecification;
import ru.learnup.bookStore.specification.util.SearchOperation;
import ru.learnup.bookStore.specification.util.SpecSearchCriteria;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/activemq")
public class MessageController {

    JmsTemplate jmsTemplate;
    BookOrderService bookOrderService;
    ModelMapper modelMapper;

    public MessageController(JmsTemplate jmsTemplate, BookOrderService bookOrderService, ModelMapper modelMapper) {
        this.jmsTemplate = jmsTemplate;
        this.bookOrderService = bookOrderService;
        this.modelMapper = modelMapper;
    }


    @GetMapping( "/report/{customer_id}")
    public ResponseEntity<List<BookOrderDTO.Response.Public>> sendOrdersOfUser(@PathVariable("customer_id") Long customerId) {
        BookOrderSpecification spec1 = new BookOrderSpecification(
                new SpecSearchCriteria("purchasedOn", SearchOperation.GREATER_THAN, LocalDate.now().minusMonths(1)));
        BookOrderSpecification spec2 = new BookOrderSpecification(
                new SpecSearchCriteria("purchasedOn", SearchOperation.LESS_THAN, LocalDate.now()));
        BookOrderSpecification spec3 = new BookOrderSpecification(
                new SpecSearchCriteria("customer", SearchOperation.EQUALITY, customerId));
        List<BookOrder> monthlyOrders = bookOrderService
                .getAllBySpec(Specification.where(spec1).and(spec2).and(spec3));
        String result = "Все заказы пользователя с id " + customerId + " за месяц: " + monthlyOrders.toString();
        jmsTemplate.convertAndSend("${topic.name}", result);
        return new ResponseEntity<>(monthlyOrders.stream().
                map(v -> modelMapper.map(v, BookOrderDTO.Response.Public.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}