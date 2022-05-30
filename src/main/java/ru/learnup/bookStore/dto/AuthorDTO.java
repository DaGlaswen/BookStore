package ru.learnup.bookStore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Value;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

public enum AuthorDTO {
    ;

    public enum Response {
        ;

        @Data
        @Schema(description = "ResponseDTO Автора")
        public static class Public implements AuthorId, FirstName, LastName, Books {
            Long authorId;
            String firstName;
            String lastName;
            @Schema(description = "Nested ресурс книги")
            Set<BookDTO.Utility.Pure> books;
        }
    }

    public enum Request {
        ;

        @Data
        public static class Public implements FirstName, LastName, Books {
            String firstName;
            String lastName;
            Set<BookDTO.Utility.Pure> books;
        }
    }

    public enum Utility {
        ;

        @Data
        @Schema(description = "Вспомогательный DTO Автора. Без roleId и nested ресурсов")
        public static class Pure implements FirstName, LastName {
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
        Set<BookDTO.Utility.Pure> getBooks();
    }


}
