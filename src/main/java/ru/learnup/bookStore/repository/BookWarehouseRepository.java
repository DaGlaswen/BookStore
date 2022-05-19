package ru.learnup.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learnup.bookStore.entity.BookWarehouse;

@Repository
public interface BookWarehouseRepository extends JpaRepository<BookWarehouse, Long> {
}
