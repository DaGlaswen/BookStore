package ru.learnup.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.learnup.bookStore.entity.Book;
import ru.learnup.bookStore.entity.BookOrder;
import ru.learnup.bookStore.entity.Customer;

@Repository
public interface OrderRepository extends JpaRepository<BookOrder, Long>, JpaSpecificationExecutor<BookOrder> {

    @Modifying
    @Query("delete from BookOrder bo where bo.totalCheckAmount = :totalAmount")
    void deleteByTotalAmount(@Param(value = "totalAmount") Integer totalAmount);

    BookOrder findFirstByTotalCheckAmount(Integer totalCheckAmount);

    @Modifying
    @Transactional
    @Query("update BookOrder bo set bo.totalCheckAmount = :totalCheckAmount, bo.customer = :customer where bo.bookOrderId = :id")
    void updateBookOrderByTotalCheckAmountAndCustomer(@Param(value = "id") Long id,
                                                      @Param(value = "totalCheckAmount") Integer totalCheckAmount,
                                                      @Param(value = "customer") Customer customer);
}
