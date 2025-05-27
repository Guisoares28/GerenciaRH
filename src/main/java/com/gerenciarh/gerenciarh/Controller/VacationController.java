package com.gerenciarh.gerenciarh.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerenciarh.gerenciarh.DtosRequest.VacationRequestDto;
import com.gerenciarh.gerenciarh.Services.VacationService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/vacation")
@CrossOrigin("*")
public class VacationController {

    private final VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @PostMapping()
    public ResponseEntity<Void> requestVacationController(@Valid @RequestBody VacationRequestDto vacationRequestDto) {
        vacationService.requestVacation(vacationRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }





}
