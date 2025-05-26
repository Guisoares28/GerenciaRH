package com.gerenciarh.gerenciarh.DtosRequest;

import jakarta.validation.constraints.NotBlank;

public record DepartamentoRequestDto(
        @NotBlank(message = "Name not given")
        String name
) {
}
