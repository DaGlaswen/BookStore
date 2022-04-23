package ru.learnup.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learnup.bookStore.entities.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findFirstByFirstNameAndLastName(String firstName, String lastName);


}
