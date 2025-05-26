package com.gerenciarh.gerenciarh.DtosRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gerenciarh.gerenciarh.Enums.EnumTypeRole;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserRequestDto(
        @NotBlank(message = "Name not given")
        String name,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        @NotBlank(message = "Contract date not given")
        LocalDate contractDate,

        @NotBlank(message = "Nickname not given")
        String nickname,

        @NotBlank(message = "Password not given")
        String password,

        @NotBlank(message = "Cpf not given")
        String cpf,

        @NotBlank(message = "Balance not given")
        BigDecimal salario,

        @NotBlank(message = "Email not given")
        String email,

        @NotBlank(message = "Cargo not given")
        String cargo,

        @NotBlank(message = "Department name not given")
        String departmentName,

        @NotBlank(message = "Role not given")
        EnumTypeRole role
) {
}
