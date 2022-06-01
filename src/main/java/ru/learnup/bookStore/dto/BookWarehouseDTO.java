package ru.learnup.bookStore.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import lombok.Data;
import lombok.Value;
import ru.learnup.bookStore.dto.BookDTO;

public enum BookWarehouseDTO {
    ;

    public enum Response {
            ;

        @Data
        public static class Public implements BookWarehouseId, BookDTOField, BooksLeftInStore {
            Long bookWarehouseId;
            BookDTO.Utility.Pure book;
            Integer booksLeftInStore;
        }
    }

    public enum Request {
        ;

        @Data
        public static class Public implements BookDTOField, BooksLeftInStore {
            BookDTO.Utility.Pure book;
            Integer booksLeftInStore;
        }
    }

    public enum Utility {
        ;
        @Data
        public static class Pure implements BookDTOField, BooksLeftInStore {
            BookDTO.Utility.Pure book;
            Integer booksLeftInStore; // ??
        }
    }

    private interface BookWarehouseId {
        @Positive Long getBookWarehouseId();
    }

    private interface BookDTOField {
        BookDTO.Utility.Pure getBook();
    }

    private interface BooksLeftInStore {
        @Min(0) Integer getBooksLeftInStore();
    }
}
