package ru.learnup.bookStore.controller;

import com.google.common.base.Joiner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookStore.dto.BookOrderDTO;
import ru.learnup.bookStore.entity.BookOrder;
import ru.learnup.bookStore.entity.Customer;
import ru.learnup.bookStore.entity.OrderDetails;
import ru.learnup.bookStore.service.interfaces.BookOrderService;
import ru.learnup.bookStore.specification.builder.BookOrderSpecificationsBuilder;
import ru.learnup.bookStore.specification.util.SearchOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/orders")
@Tag(name="OrderController", description="Контроллирует CRUD операции с энтити BookOrder")
public class OrderController {

    BookOrderService bookOrderService;
    ModelMapper modelMapper;

    public OrderController(BookOrderService bookOrderService, ModelMapper modelMapper) {
        this.bookOrderService = bookOrderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(
            summary = "Get запрос заказов",
            description = "Get запрос заказов с реквест параметрами page, size, sort. DefaultValue у pageable: page=0," +
                    "size=5, sort=title"
    )
    public ResponseEntity<List<BookOrderDTO.Response.Public>> getBookOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                       @RequestParam(value = "size", defaultValue = "5") int size,
                                                                       @RequestParam(value = "sort", defaultValue = "title") String sort,
                                                                       Pageable pageable) {
        List<BookOrder> bookOrders = bookOrderService.getBookOrders(pageable);
        List<BookOrderDTO.Response.Public> mappedBookOrders = new ArrayList<>(bookOrders.size());
        for (BookOrder bookOrder :
                bookOrders) {
            mappedBookOrders.add(modelMapper.map(bookOrder, BookOrderDTO.Response.Public.class));
        }
        return new ResponseEntity<>(mappedBookOrders, HttpStatus.OK);
    }

    @GetMapping("/{bookOrderId}")
    public ResponseEntity<BookOrderDTO.Response.Public> getBookOrderOrderById(@PathVariable Long bookOrderId) {
        BookOrder bookOrder = bookOrderService.getById(bookOrderId);
        return new ResponseEntity<>(modelMapper.map(bookOrder, BookOrderDTO.Response.Public.class), HttpStatus.OK);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<BookOrderDTO.Response.Public> createBookOrder(@RequestBody BookOrderDTO.Request.Public BookOrder) {
        return new ResponseEntity<>(modelMapper.map(bookOrderService
                .createBookOrder(modelMapper.map(BookOrder, BookOrder.class)), BookOrderDTO.Response.Public.class),
                HttpStatus.CREATED);
    }

    @GetMapping("/spec")
    public ResponseEntity<BookOrderDTO.Response.Public> getAllBySpecification(@RequestParam(value = "search") String search){
        BookOrderSpecificationsBuilder builder = new BookOrderSpecificationsBuilder();
        String operationSetExper = Joiner.on("|")
                .join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }

        Specification<BookOrder> spec = builder.build();
        return new ResponseEntity<>(modelMapper.map(bookOrderService.getAllBySpec(spec),
                BookOrderDTO.Response.Public.class), HttpStatus.OK);
    }

    @PutMapping("/{bookOrderId}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<BookOrderDTO.Response.Public> updateBookOrder(@PathVariable("bookOrderId") Long bookOrderId, @RequestBody BookOrderDTO.Request.Public body) {
        BookOrder bookOrder = bookOrderService.getById(bookOrderId);
        if (!bookOrder.getPurchasedOn().equals(body.getPurchasedOn())) {
            bookOrder.setPurchasedOn(body.getPurchasedOn());
        }
        if (!bookOrder.getTotalCheckAmount().equals(body.getTotalCheckAmount())) {
            bookOrder.setTotalCheckAmount(body.getTotalCheckAmount());
        }
        Customer customerEntity = modelMapper.map(body.getCustomer(), Customer.class);
        if (!bookOrder.getCustomer().equals(customerEntity)) {
            bookOrder.setCustomer(customerEntity);
        }
        List<OrderDetails> orderDetailsEntities = body.getOrderDetails()
                .stream().map(v -> modelMapper.map(v, OrderDetails.class)).collect(Collectors.toCollection(ArrayList::new));
        if (!bookOrder.getOrderDetailsList().equals(orderDetailsEntities)) {
            bookOrder.setOrderDetailsList(orderDetailsEntities);
        }

        BookOrder updatedBookOrder = bookOrderService.updateBookOrder(bookOrder);

        return new ResponseEntity<>(modelMapper.map(updatedBookOrder, BookOrderDTO.Response.Public.class), HttpStatus.OK);
    }

    @DeleteMapping
    @Secured({"ROLE_ADMIN"})
    public Boolean deleteBookOrder(@PathVariable Long id) {
        return bookOrderService.deleteBookOrder(id);
    }
}
