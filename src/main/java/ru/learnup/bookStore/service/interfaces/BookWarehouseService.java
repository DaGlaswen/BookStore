package ru.learnup.bookStore.service.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.entity.BookWarehouse;

import java.util.List;

public interface BookWarehouseService {
    BookWarehouse createBookWarehouse(BookWarehouse bookWarehouse);
    BookWarehouse updateBookWarehouse(BookWarehouse bookWarehouse);
    Boolean deleteBookWarehouse(Long id);
    BookWarehouse getById(Long id);
    List<BookWarehouse> getBookWarehouses(Pageable pageable);
    List<BookWarehouse> getAllBySpec(Specification<BookWarehouse> spec);
}
