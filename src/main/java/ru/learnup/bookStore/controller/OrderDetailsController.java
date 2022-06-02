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
import ru.learnup.bookStore.dto.OrderDetailsDTO;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.entity.BookOrder;
import ru.learnup.bookStore.entity.OrderDetails;
import ru.learnup.bookStore.service.interfaces.OrderDetailsService;
import ru.learnup.bookStore.specification.builder.OrderDetailsSpecificationsBuilder;
import ru.learnup.bookStore.specification.util.SearchOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/orderDetails")
@Tag(name="OrderDetailsController", description="Контроллирует CRUD операции с энтити OrderDetails")
public class OrderDetailsController {

    OrderDetailsService orderDetailsService;
    ModelMapper modelMapper;

    public OrderDetailsController(OrderDetailsService orderDetailsService, ModelMapper modelMapper) {
        this.orderDetailsService = orderDetailsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(
            summary = "Get запрос деталей заказов",
            description = "Get запрос деталей заказов с реквест параметрами page, size, sort. DefaultValue у pageable: page=0," +
                    "size=5, sort=title"
    )
    public ResponseEntity<List<OrderDetailsDTO.Response.Public>> getOrderDetails(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                 @RequestParam(value = "size", defaultValue = "5") int size,
                                                                                 @RequestParam(value = "sort", defaultValue = "title") String sort,
                                                                                 Pageable pageable) {
        List<OrderDetails> orderDetailsList = orderDetailsService.getOrderDetails(pageable);
        List<OrderDetailsDTO.Response.Public> mappedOrderDetails = new ArrayList<>(orderDetailsList.size());
        for (OrderDetails orderDetails :
                orderDetailsList) {
            mappedOrderDetails.add(modelMapper.map(orderDetails, OrderDetailsDTO.Response.Public.class));
        }
        return new ResponseEntity<>(mappedOrderDetails, HttpStatus.OK);
    }

    @GetMapping("/{OrderDetailsId}")
    public ResponseEntity<OrderDetailsDTO.Response.Public> getOrderDetailsById(@PathVariable Long orderDetailsId) {
        OrderDetails orderDetails = orderDetailsService.getById(orderDetailsId);
        return new ResponseEntity<>(modelMapper.map(orderDetails, OrderDetailsDTO.Response.Public.class), HttpStatus.OK);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<OrderDetailsDTO.Response.Public> createOrderDetails(@RequestBody OrderDetailsDTO.Request.Public orderDetails) {
        return new ResponseEntity<>(modelMapper.map(orderDetailsService
                .createOrderDetails(modelMapper.map(orderDetails, OrderDetails.class)), OrderDetailsDTO.Response.Public.class),
                HttpStatus.CREATED);
    }

    @GetMapping("/spec")
    public ResponseEntity<OrderDetailsDTO.Response.Public> getAllBySpecification(@RequestParam(value = "search") String search){
        OrderDetailsSpecificationsBuilder builder = new OrderDetailsSpecificationsBuilder();
        String operationSetExper = Joiner.on("|")
                .join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }

        Specification<OrderDetails> spec = builder.build();
        return new ResponseEntity<>(modelMapper.map(orderDetailsService.getAllBySpec(spec),
                OrderDetailsDTO.Response.Public.class), HttpStatus.OK);
    }

    @PutMapping("/{orderDetailsId}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<OrderDetailsDTO.Response.Public> updateOrderDetails(@PathVariable("orderDetailsId") Long orderDetailsId, @RequestBody OrderDetailsDTO.Request.Public body) {
        OrderDetails orderDetails = orderDetailsService.getById(orderDetailsId);
        if (!orderDetails.getQuantity().equals(body.getQuantity())) {
            orderDetails.setQuantity(body.getQuantity());
        }
        if (!orderDetails.getTotalPrice().equals(body.getTotalPrice())) {
            orderDetails.setTotalPrice(body.getTotalPrice());
        }
        Book bookEntity = modelMapper.map(body.getBook(), Book.class);
        if (!orderDetails.getBook().equals(bookEntity)) {
            orderDetails.setBook(bookEntity);
        }
        BookOrder bookOrderEntity = modelMapper.map(body.getBookOrder(), BookOrder.class);
        if (!orderDetails.getBookOrder().equals(bookOrderEntity)) {
            orderDetails.setBookOrder(bookOrderEntity);
        }

        OrderDetails updatedOrderDetails = orderDetailsService.updateOrderDetails(orderDetails);

        return new ResponseEntity<>(modelMapper.map(updatedOrderDetails,
                OrderDetailsDTO.Response.Public.class), HttpStatus.OK);
    }

    @DeleteMapping
    @Secured({"ROLE_ADMIN"})
    public Boolean deleteOrderDetails(@PathVariable Long id) {
        return orderDetailsService.deleteOrderDetails(id);
    }

}
