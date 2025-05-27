package com.gerenciarh.gerenciarh.Utils;

import com.gerenciarh.gerenciarh.DtosRequest.VacationRequestDto;
import com.gerenciarh.gerenciarh.DtosResponse.VacationResponseDto;
import com.gerenciarh.gerenciarh.Enums.EnumTypeVacationStatus;
import com.gerenciarh.gerenciarh.Models.Vacation;

import java.util.List;

public class VacationUtils {


    public static Vacation fromRequestForDto(VacationRequestDto vacationRequestDto) {
        Vacation vacation = new Vacation();
        vacation.setData(vacationRequestDto.date());
        vacation.setStatus(EnumTypeVacationStatus.PENDENTE);
        return vacation;
    }

    public static List<VacationResponseDto> listVacationModelFromListDtoResponse(List<Vacation> vacations){
        try{
            return vacations.stream()
                    .map(vacation -> new VacationResponseDto(
                            vacation.getId(),
                            vacation.getUser().getName(),
                            vacation.getData(),
                            vacation.getStatus()
                    ))
                    .toList();
        }catch (Exception ex){
            throw new RuntimeException("Não foi possível converter Vacation para Dto");
        }

    }

}
