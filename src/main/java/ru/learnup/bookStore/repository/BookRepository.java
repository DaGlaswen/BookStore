package ru.learnup.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.learnup.bookStore.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "select b.book_id, b.number_of_pages, b.price, b.title, b.year_published from book_authors as ba " +
            "inner join book as b on ba.book_id = b.book_id " +
            "inner join author as a on a.author_id = ba.author_id " +
            "where a.first_name = :firstName and a.last_name =:lastName", nativeQuery = true)
    List<Book> findBooksByAuthor(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
