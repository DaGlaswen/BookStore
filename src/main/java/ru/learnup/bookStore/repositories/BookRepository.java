package ru.learnup.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.learnup.bookStore.entities.Author;
import ru.learnup.bookStore.entities.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "select * from book_authors as ba " +
            "inner join Book as b on ba.book_id = b.id " +
            "inner join Author as a on a.id = ba.author_id " +
            "where a.first_name = :firstName and a.last_name =:lastName", nativeQuery = true)
    List<Book> findBooksByAuthor(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
