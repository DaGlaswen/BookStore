package ru.learnup.bookStore.dto;

import lombok.Data;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

public enum BookOrderDTO {
    ;

    public enum Response {
        ;

        @Data
        public static class Public implements BookOrderId, CustomerDTOField, OrderDetailsDTOListField, TotalCheckAmount, PurchasedOn {

            Long bookOrderId;
            CustomerDTO.Utility.Pure customer;
            List<OrderDetailsDTO.Utility.Pure> orderDetails;
            Integer totalCheckAmount;
            LocalDate purchasedOn;

        }
    }

    public enum Request {
        ;

        @Data
        public static class Public implements CustomerDTOField, OrderDetailsDTOListField, TotalCheckAmount, PurchasedOn {

            CustomerDTO.Utility.Pure customer;
            List<OrderDetailsDTO.Utility.Pure> orderDetails;
            Integer totalCheckAmount;
            LocalDate purchasedOn;

        }
    }

    public enum Utility {
        ;

        @Data
        public static class Pure implements CustomerDTOField, OrderDetailsDTOListField, TotalCheckAmount, PurchasedOn {
            CustomerDTO.Utility.Pure customer;
            List<OrderDetailsDTO.Utility.Pure> orderDetails;
            Integer totalCheckAmount;
            LocalDate purchasedOn;

        }

    }

    private interface BookOrderId {
        @Positive Long getBookOrderId();
    }

    private interface PurchasedOn {
        LocalDate getPurchasedOn();
    }

    private interface CustomerDTOField {
        CustomerDTO.Utility.Pure getCustomer();
    }

    private interface OrderDetailsDTOListField {
        List<OrderDetailsDTO.Utility.Pure> getOrderDetails();
    }

    private interface TotalCheckAmount {
        Integer getTotalCheckAmount();
    }
}

