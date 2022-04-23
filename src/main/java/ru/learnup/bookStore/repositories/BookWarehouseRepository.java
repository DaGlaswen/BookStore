package ru.learnup.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.learnup.bookStore.entities.BooksWarehouse;

public interface BookWarehouseRepository extends JpaRepository<BooksWarehouse, Long> {
}
