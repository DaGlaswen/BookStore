package ru.learnup.bookStore.dto;

import lombok.Data;
import lombok.Value;
import ru.learnup.bookStore.entity.Customer;

import javax.validation.constraints.Positive;
import java.util.List;

public enum BookOrderDTO {
    ;

    public enum Response {
        ;

        @Data
        public static class Public implements BookOrderId, CustomerDTOField, OrderDetailsDTOListField, TotalCheckAmount {

            Long bookOrderId;
            CustomerDTO.Utility.Pure customer;
            List<OrderDetailsDTO.Utility.Pure> orderDetails;
            Integer totalCheckAmount;

        }
    }

    public enum Request {
        ;

        @Data
        public static class Public implements CustomerDTOField, OrderDetailsDTOListField, TotalCheckAmount {

            CustomerDTO.Utility.Pure customer;
            List<OrderDetailsDTO.Utility.Pure> orderDetails;
            Integer totalCheckAmount;
        }
    }

    public enum Utility {
        ;

        @Data
        public static class Pure implements CustomerDTOField, OrderDetailsDTOListField, TotalCheckAmount {
            CustomerDTO.Utility.Pure customer; // ??
            List<OrderDetailsDTO.Utility.Pure> orderDetails; // ??
            Integer totalCheckAmount;
        }

    }

    private interface BookOrderId {
        @Positive Long getBookOrderId();
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

