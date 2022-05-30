package ru.learnup.bookStore.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.bookStore.entity.BookWarehouse;
import ru.learnup.bookStore.repository.BookWarehouseRepository;
import ru.learnup.bookStore.service.interfaces.BookWarehouseService;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookWarehouseServiceImpl implements BookWarehouseService {

    BookWarehouseRepository bookWarehouseRepository;

    public BookWarehouseServiceImpl(BookWarehouseRepository bookWarehouseRepository) {
        this.bookWarehouseRepository = bookWarehouseRepository;
    }

    @Override
    @Transactional
    public BookWarehouse createBookWarehouse(BookWarehouse bookWarehouse) {
        return bookWarehouseRepository.save(bookWarehouse);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public BookWarehouse updateBookWarehouse(BookWarehouse bookWarehouse) {
        try {
            return bookWarehouseRepository.save(bookWarehouse);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic log exception for bookWarehouse {}", bookWarehouse.getBookId());
            throw e;
        }
    }

    @Override
    @Transactional
    public Boolean deleteBookWarehouse(Long id) {
        bookWarehouseRepository.delete(bookWarehouseRepository.getById(id));
        return true;
    }

    @Override
    public BookWarehouse getById(Long id) {
        return bookWarehouseRepository.getById(id);
    }

    @Override
    public List<BookWarehouse> getBookWarehouses(Pageable pageable) {
        return bookWarehouseRepository.findAll(pageable).stream().collect(Collectors.toList());
    }

    @Override
    public List<BookWarehouse> getAllBySpec(Specification<BookWarehouse> spec) {
        return bookWarehouseRepository.findAll(spec);
    }
}
