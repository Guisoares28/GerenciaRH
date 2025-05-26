package com.gerenciarh.gerenciarh.DtosResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserResponseDto(
        String name,
        String nickname,
        String password,
        LocalDate contractDate,
        String cpf,
        BigDecimal salario,
        String email,
        String cargo,
        String departamento
) {
}
