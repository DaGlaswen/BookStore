package ru.learnup.bookStore.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.entity.BookOrder;
import ru.learnup.bookStore.repository.OrderRepository;
import ru.learnup.bookStore.service.interfaces.BookOrderService;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookOrderServiceImpl implements BookOrderService {

    OrderRepository orderRepository;

    public BookOrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public BookOrder createBookOrder(BookOrder bookOrder) {
        return orderRepository.save(bookOrder);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public BookOrder updateBookOrder(BookOrder bookOrder) {
        try {
            return orderRepository.save(bookOrder);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic log exception for order {}", bookOrder.getBookOrderId());
            throw e;
        }
    }

    @Override
    @Transactional
    public Boolean deleteBookOrder(Long id) {
        orderRepository.delete(orderRepository.getById(id));
        return true;
    }

    @Override
    public BookOrder getById(Long id) {
        return orderRepository.getById(id);
    }

    @Override
    public List<BookOrder> getBookOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).stream().collect(Collectors.toList());
    }

    @Override
    public List<BookOrder> getAllBySpec(Specification<BookOrder> spec) {
        return orderRepository.findAll(spec);
    }
}
