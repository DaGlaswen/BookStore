package ru.learnup.bookStore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Set;

public enum UserDTO {
    ;

    public enum Response {
        ;

        @Data
        public static class Public implements UserDTO.UserId, UserDTO.Username, UserDTO.Password, UserDTO.Roles {
            Long userId;
            String username;
            String password;
            Set<RoleDTO.Utility.Pure> roles;
        }
    }

    public enum Request {
        ;

        @Data
        public static class Public implements UserDTO.Username, UserDTO.Password, UserDTO.Roles {
            String username;
            String password;
            Set<RoleDTO.Utility.Pure> roles;
        }
    }

    public enum Utility {
        ;

        @Data
        public static class Pure implements UserDTO.Username, UserDTO.Password {
            String username;
            String password;
        }
    }

    private interface UserId {
        @Positive Long getUserId();
    }

    private interface Username {
        @NotBlank String getUsername();
    }

    private interface Password {
        @NotBlank String getPassword();
    }

    private interface Roles {
        Set<RoleDTO.Utility.Pure> getRoles();
    }


}