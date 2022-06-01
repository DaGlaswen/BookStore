package ru.learnup.bookStore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.learnup.bookStore.entity.Author;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;


public enum BookDTO {
    ;

    public enum Response {
        ;

        @Data
        @Schema(description = "Response body")
        public static class Public implements BookId, Title, YearPublished, NumberOfPages, Price, BookWarehouseDTOField {
            Long bookId;
            String title;
            Integer yearPublished;
            Integer numberOfPages;
            Integer price;
            BookWarehouseDTO.Utility.Pure bookWarehouse;
            List<OrderDetailsDTO.Utility.Pure> orderDetailsDTOList;
            Set<AuthorDTO.Utility.Pure> authorDTOs;
        }
    }

    public enum Request {
        ;

        @Data
        @Schema(description = "Request Body of a Book")
        public static class Public implements YearPublished, Title, NumberOfPages, Price {
            String title;
            Integer yearPublished;
            Integer numberOfPages;
            Integer price;
            Set<AuthorDTO.Utility.Pure> authorDTOs;
            BookWarehouseDTO.Utility.Pure bookWarehouse;
            List<OrderDetailsDTO.Utility.Pure> orderDetailsDTOList;
        }
    }

    public enum Utility {
        ;

        @Data
        public static class Pure implements Title, YearPublished, NumberOfPages, Price {
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
        Set<Author> getAuthorDTOs();
    }
}
