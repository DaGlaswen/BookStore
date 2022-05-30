package ru.learnup.bookStore.service.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.entity.BookOrder;

import java.util.List;

public interface BookOrderService {
    BookOrder createBookOrder(BookOrder bookOrder);
    BookOrder updateBookOrder(BookOrder bookOrder);
    Boolean deleteBookOrder(Long id);
    BookOrder getById(Long id);
    List<BookOrder> getBookOrders(Pageable pageable);
    List<BookOrder> getAllBySpec(Specification<BookOrder> spec);
}
