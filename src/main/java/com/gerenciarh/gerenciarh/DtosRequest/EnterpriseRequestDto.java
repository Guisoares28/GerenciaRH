package com.gerenciarh.gerenciarh.DtosRequest;

import jakarta.validation.constraints.NotBlank;

public record EnterpriseRequestDto(
        @NotBlank(message = "Name not given")
        String name,

        @NotBlank(message = "CNPJ not given")
        String cnpj,

        @NotBlank(message = "Number phone not given")
        String phone,

        @NotBlank(message = "Email not given")
        String email
) {
}
