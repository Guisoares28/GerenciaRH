package com.gerenciarh.gerenciarh.DtosRequest;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

public record VacationRequestDto(
        @JsonFormat(pattern = "dd/MM/yyyy")
        @NotNull(message = "Date not given")
        LocalDate date
) {
}
