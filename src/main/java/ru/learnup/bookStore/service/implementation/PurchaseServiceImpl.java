package ru.learnup.bookStore.service.implementation;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.entity.BookOrder;
import ru.learnup.bookStore.entity.BookWarehouse;
import ru.learnup.bookStore.entity.Customer;
import ru.learnup.bookStore.entity.OrderDetails;
import ru.learnup.bookStore.service.interfaces.BookOrderService;
import ru.learnup.bookStore.service.interfaces.BookService;
import ru.learnup.bookStore.service.interfaces.BookWarehouseService;
import ru.learnup.bookStore.service.interfaces.CustomerService;
import ru.learnup.bookStore.service.interfaces.PurchaseService;

import java.time.LocalDate;
import java.util.HashMap;

public class PurchaseServiceImpl implements PurchaseService {

    BookService bookService;
    CustomerService customerService;
    BookOrderService bookOrderService;
    BookWarehouseService bookWarehouseService;

    public PurchaseServiceImpl(BookService bookService, CustomerService customerService, BookOrderService bookOrderService, BookWarehouseService bookWarehouseService) {
        this.bookService = bookService;
        this.customerService = customerService;
        this.bookOrderService = bookOrderService;
        this.bookWarehouseService = bookWarehouseService;
    }

    @Override
    public BookOrder buyBook(Long customerId, @Schema(description = "Хеш мап, где ключ - id покупаемой книги," +
            " значение - количество покупаемых экземпляров") HashMap<Long, Integer> bookMap) {

        Customer customer = customerService.getById(customerId);

        BookOrder bookOrder = BookOrder.builder()
                .customer(customer)
                .purchasedOn(LocalDate.now())
                .build();

        Integer totalCheckAmount = 0;
        for (Long bookId :
                bookMap.keySet()) {
            Book book = bookService.getById(bookId);
            BookWarehouse bookWarehouse = bookWarehouseService.getById(bookId);
            Integer currentStock = bookWarehouse.getBooksLeftInStock();
            Integer quantity = bookMap.get(bookId);
            bookWarehouse.setBooksLeftInStock(
                    currentStock - quantity
            );
            bookWarehouseService.updateBookWarehouse(bookWarehouse);
            Integer price = book.getPrice();
            Integer totalPrice = price * quantity;
            OrderDetails orderDetails = OrderDetails.builder()
                    .book(book)
                    .quantity(quantity)
                    .totalPrice(totalPrice)
                    .build();
            totalCheckAmount += totalPrice;
            bookOrder.getOrderDetailsList().add(orderDetails);
        }
        bookOrder.setTotalCheckAmount(totalCheckAmount);

        return bookOrderService.createBookOrder(bookOrder);
    }
}
