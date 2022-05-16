package ru.learnup.bookStore.dto;

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
        public static class Public implements BookId, Title, YearPublished, NumberOfPages, Price, BookWarehouseDTOField {
            Long bookId;
            String title;
            Integer yearPublished;
            Integer numberOfPages;
            Integer price;
            BookWarehouseDTO.Utility.Pure bookWarehouse;
            List<OrderDetailsDTO.Utility.Pure> orderDetailsDTOList;
            List<AuthorDTO> authorDTOs;
        }
    }

    public enum Request {
        ;

        @Data
        public static class Public implements YearPublished, Title, NumberOfPages, Price {
            String title;
            Integer yearPublished;
            Integer numberOfPages;
            Integer price;
            List<AuthorDTO> authorDTOs;
            BookWarehouseDTO.Utility.Pure bookWarehouse;
            List<OrderDetailsDTO> orderDetailsDTOList;
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
