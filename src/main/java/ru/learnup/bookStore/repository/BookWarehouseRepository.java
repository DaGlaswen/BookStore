package ru.learnup.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learnup.bookStore.entity.BooksWarehouse;

@Repository
public interface BookWarehouseRepository extends JpaRepository<BooksWarehouse, Long> {
}
