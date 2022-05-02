package ru.learnup.bookStore.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learnup.bookStore.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findFirstByFirstNameAndLastName(String firstName, String lastName);


    Author findAll(Specification<Author> specification);
}
