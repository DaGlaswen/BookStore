package ru.learnup.bookStore.service.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.learnup.bookStore.entity.OrderDetails;

import java.util.List;

public interface OrderDetailsService {
    OrderDetails createOrderDetails(OrderDetails orderDetails);
    OrderDetails updateOrderDetails(OrderDetails orderDetails);
    Boolean deleteOrderDetails(Long id);
    OrderDetails getById(Long id);
    List<OrderDetails> getOrderDetails(Pageable pageable);
    List<OrderDetails> getAllBySpec(Specification<OrderDetails> spec);
}
