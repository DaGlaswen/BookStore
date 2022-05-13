package ru.learnup.bookStore.dto;

import lombok.Data;
import lombok.Value;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

public enum AuthorDTO {
    ;

    public enum Response {
        ;

        @Data
        public static class Public extends RepresentationModel<Public> implements AuthorId, FirstName, LastName, Books {
            Long authorId;
            String firstName;
            String lastName;
            List<BookDTO.Utility.Pure> books;
        }
    }

    public enum Request {
        ;

        @Data
        public static class Public implements AuthorId, FirstName, LastName {
            Long authorId;
            String firstName;
            String lastName;
        }
    }

    public enum Utility {
        ;

        @Data
        public static class Pure implements AuthorId, FirstName, LastName {
            Long authorId;
            String firstName;
            String lastName;
        }
    }

    private interface AuthorId {
        @Positive Long getAuthorId();
    }

    private interface FirstName {
        @NotBlank String getFirstName();
    }

    private interface LastName {
        @NotBlank String getLastName();
    }

    private interface Books {
        List<BookDTO.Utility.Pure> getBooks();
    }


}
