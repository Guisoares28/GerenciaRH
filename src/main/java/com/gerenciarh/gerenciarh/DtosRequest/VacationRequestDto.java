package com.gerenciarh.gerenciarh.DtosRequest;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;

public record VacationRequestDto(
        @JsonFormat(pattern = "dd/MM/yyyy")
        @NotBlank(message = "Date not given")
        LocalDate date
) {
}
