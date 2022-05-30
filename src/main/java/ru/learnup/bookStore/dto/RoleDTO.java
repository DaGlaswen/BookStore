package ru.learnup.bookStore.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public enum RoleDTO {
    ;

    public enum Response {
        ;

        @Data
        public static class Public implements RoleDTO.RoleId, RoleDTO.Role {
            Long roleId;
            String Role;
        }
    }

    public enum Request {
        ;

        @Data
        public static class Public implements RoleDTO.Role {
            String Role;
        }
    }

    public enum Utility {
        ;

        @Data
        public static class Pure implements RoleDTO.Role {
            String Role;
        }
    }

    private interface RoleId {
        @Positive Long getRoleId();
    }

    private interface Role {
        @NotBlank String getRole();
    }
}
