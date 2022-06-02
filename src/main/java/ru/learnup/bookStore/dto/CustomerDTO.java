package ru.learnup.bookStore.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

public enum CustomerDTO {
    ;

    public enum Utility {
        ;

        @Data
        public static class Pure implements FirstName, LastName, BirthDate {
            String firstName;
            String lastName;
            LocalDate birthDate;
        }
    }

    public enum Response {
        ;

        @Data
        public static class Public implements CustomerId, FirstName, LastName, BirthDate, BookOrdersDTOField {
            Long customerId;
            String firstName;
            String lastName;
            LocalDate birthDate;
            List<BookOrderDTO.Utility.Pure> bookOrders;
        }
    }

    public enum Request {
        ;

        @Data
        public static class Public implements FirstName, LastName, BirthDate, BookOrdersDTOField {
            Long customerId;
            String firstName;
            String lastName;
            LocalDate birthDate;
            List<BookOrderDTO.Utility.Pure> bookOrders;

        }
    }

    private interface CustomerId {
        @Positive Long getCustomerId();
    }

    private interface FirstName {
        @NotBlank String getFirstName();
    }

    private interface LastName {
        @NotBlank String getLastName();
    }

    private interface BirthDate {
        LocalDate getBirthDate();
    }

    private interface BookOrdersDTOField {
        List<BookOrderDTO.Utility.Pure> getBookOrders();
    }
}
