package com.gerenciarh.gerenciarh.DtosRequest;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDto(

        @NotBlank(message = "Nickname not given")
        String nickname,

        @NotBlank(message = "Password not given")
        String password
) {
}
