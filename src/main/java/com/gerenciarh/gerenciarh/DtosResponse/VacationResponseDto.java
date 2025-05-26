package com.gerenciarh.gerenciarh.DtosResponse;

import com.gerenciarh.gerenciarh.Enums.EnumTypeVacationStatus;

import java.time.LocalDate;

public record VacationResponseDto(
        Long id,
        String username,
        LocalDate data,
        EnumTypeVacationStatus status
) {
}
