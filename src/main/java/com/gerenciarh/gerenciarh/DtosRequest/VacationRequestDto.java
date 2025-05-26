package com.gerenciarh.gerenciarh.DtosRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gerenciarh.gerenciarh.Enums.EnumTypeVacationStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record VacationRequestDto(
        @JsonFormat(pattern = "dd/MM/yyyy")
        @NotBlank(message = "Date not given")
        LocalDate date
) {
}
