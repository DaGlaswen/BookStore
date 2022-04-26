package ru.learnup.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learnup.bookStore.entities.BooksWarehouse;

@Repository
public interface BookWarehouseRepository extends JpaRepository<BooksWarehouse, Long> {
}
