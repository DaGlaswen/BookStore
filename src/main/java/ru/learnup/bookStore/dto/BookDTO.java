package ru.learnup.bookStore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.hateoas.RepresentationModel;
import ru.learnup.bookStore.entity.Author;
import ru.learnup.bookStore.dto.BookWarehouseDTO;
import ru.learnup.bookStore.entity.Book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;


public enum BookDTO {
    ;

    public enum Response {
        ;

        @Data
        @Schema(description = "ResponseDTO Автора")
        public static class Public implements BookId, Title, YearPublished, NumberOfPages, Price, BookWarehouseDTOField {
            Long bookId;
            String title;
            Integer yearPublished;
            Integer numberOfPages;
            Integer price;
            @Schema(description = "Nested ресурс склада")
            BookWarehouseDTO.Utility.Pure bookWarehouse;
            @Schema(description = "Nested ресурс деталей заказа")
            List<OrderDetailsDTO.Utility.Pure> orderDetailsDTOList;
            @Schema(description = "Nested ресурс авторов")
            List<AuthorDTO.Utility.Pure> authorDTOs;
        }
    }

    public enum Request {
        ;

        @Data
        @Schema(description = "RequestDTO Книги")
        public static class Public implements YearPublished, Title, NumberOfPages, Price {
            String title;
            Integer yearPublished;
            Integer numberOfPages;
            Integer price;
            @Schema(description = "Nested ресурс авторов")
            List<AuthorDTO.Utility.Pure> authorDTOs;
            @Schema(description = "Nested ресурс склада")
            BookWarehouseDTO.Utility.Pure bookWarehouse;
            @Schema(description = "Nested ресурс деталей заказа")
            List<OrderDetailsDTO.Utility.Pure> orderDetailsDTOList;
        }

        @Data
        public static class Id implements BookId {
            Long bookId;
        }
    }

    public enum Utility {
        ;

        @Data
        public static class Pure implements BookId, Title, YearPublished, NumberOfPages, Price {
            Long bookId;
            String title;
            Integer yearPublished;
            Integer numberOfPages;
            Integer price;
        }
    }

    private interface BookId {
        @Positive Long getBookId();
    }

    private interface Title {
        @NotBlank String getTitle();
    }

    private interface YearPublished {
        @Positive Integer getYearPublished();
    }

    private interface NumberOfPages {
        @Positive Integer getNumberOfPages();
    }

    private interface Price {
        @Positive Integer getPrice();
    }

    private interface BookWarehouseDTOField {
        BookWarehouseDTO.Utility.Pure getBookWarehouse();
    }

    private interface OrderDetailsList {
        List<OrderDetailsDTO> getOrderDetailsDTOList();
    }

    private interface Authors {
        List<Author> getAuthorDTOs();
    }
}
