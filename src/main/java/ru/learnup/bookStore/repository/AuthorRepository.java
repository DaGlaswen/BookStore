package ru.learnup.bookStore.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.entity.Book;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {

    Author findFirstByFirstNameAndLastName(String firstName, String lastName);
}
