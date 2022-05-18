package ru.learnup.bookStore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(description = "ResponseDTO Автора")
        public static class Public implements AuthorId, FirstName, LastName, Books {
            Long authorId;
            String firstName;
            String lastName;
            @Schema(description = "Nested ресурс книги")
            List<BookDTO.Utility.Pure> books;
        }
    }

    public enum Request {
        ;

        @Data
        @Schema(description = "RequestDTO Автора")
        public static class Public implements FirstName, LastName {
            String firstName;
            String lastName;
        }
    }

    public enum Utility {
        ;

        @Data
        @Schema(description = "Вспомогательный DTO Автора. Без id и nested ресурсов")
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
        List<BookDTO.Utility.Pure> getBooks();
    }


}
