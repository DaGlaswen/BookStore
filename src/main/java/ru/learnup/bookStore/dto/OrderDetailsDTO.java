package ru.learnup.bookStore.dto;

import lombok.Data;
import lombok.Value;

import javax.persistence.criteria.Order;
import javax.validation.constraints.Positive;

public enum OrderDetailsDTO {
    ;

    public enum Utility {
        ;

        @Data
        public static class Pure implements BookOrderDTOField, BookDTOField, Quantity, TotalPrice {
            BookDTO.Utility.Pure book;
            BookOrderDTO.Utility.Pure bookOrder;
            Integer quantity;
            Integer totalPrice;
        }
    }

    public enum Response {
        ;

        @Data
        public static class Public implements OrderDetailsId, BookOrderDTOField, BookDTOField, Quantity, TotalPrice {
            Long orderDetailsId;
            BookDTO.Utility.Pure book;
            BookOrderDTO.Utility.Pure bookOrder;
            Integer quantity;
            Integer totalPrice;
        }
    }

    public enum Request {
        ;

        @Data
        public static class Public implements OrderDetailsId {
            Long orderDetailsId;
        }
    }

    private interface OrderDetailsId {
        @Positive Long getOrderDetailsId();
    }

    private interface BookOrderDTOField {
        BookOrderDTO.Utility.Pure getBookOrder();
    }

    private interface BookDTOField {
        BookDTO.Utility.Pure getBook();
    }

    private interface Quantity {
        Integer getQuantity();
    }

    private interface TotalPrice {
        Integer getTotalPrice();
    }

}
