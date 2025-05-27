package com.gerenciarh.gerenciarh.DtosRequest;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gerenciarh.gerenciarh.Enums.EnumTypeRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDto(
        @NotBlank(message = "Name not given")
        String name,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        @NotNull(message = "Contract date not given")
        LocalDate contractDate,

        @NotBlank(message = "Nickname not given")
        String nickname,

        @NotBlank(message = "Password not given")
        String password,

        @NotBlank(message = "Cpf not given")
        String cpf,

        @NotNull(message = "Salary not given")
        BigDecimal salario,

        @NotBlank(message = "Email not given")
        String email,

        @NotBlank(message = "Cargo not given")
        String cargo,

        @NotBlank(message = "Department name not given")
        String departmentName,

        @NotNull(message = "Role not given")
        EnumTypeRole role
) {
}
