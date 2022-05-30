package ru.learnup.bookStore.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.bookStore.entity.OrderDetails;
import ru.learnup.bookStore.repository.OrderDetailsRepository;
import ru.learnup.bookStore.service.interfaces.OrderDetailsService;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    @Transactional
    public OrderDetails createOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public OrderDetails updateOrderDetails(OrderDetails orderDetails) {
        try {
            return orderDetailsRepository.save(orderDetails);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic log exception for orderDetails {}", orderDetails.getOrderDetailsId());
            throw e;
        }
    }

    @Override
    @Transactional
    public Boolean deleteOrderDetails(Long id) {
        orderDetailsRepository.delete(orderDetailsRepository.getById(id));
        return true;
    }

    @Override
    public OrderDetails getById(Long id) {
        return orderDetailsRepository.getById(id);
    }

    @Override
    public List<OrderDetails> getOrderDetails(Pageable pageable) {
        return orderDetailsRepository.findAll(pageable).stream().collect(Collectors.toList());
    }

    @Override
    public List<OrderDetails> getAllBySpec(Specification<OrderDetails> spec) {
        return orderDetailsRepository.findAll(spec);
    }
}
