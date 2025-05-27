package com.gerenciarh.gerenciarh.Controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerenciarh.gerenciarh.DtosRequest.VacationRequestDto;
import com.gerenciarh.gerenciarh.DtosRequest.VacationUpdateRequestDto;
import com.gerenciarh.gerenciarh.DtosResponse.VacationResponseDto;
import com.gerenciarh.gerenciarh.Enums.EnumTypeVacationStatus;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Services.VacationService;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUserHolder;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUtils;

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

    @GetMapping()
    public ResponseEntity<List<VacationResponseDto>> findAllVacationsController() {
        List<VacationResponseDto> vacations = vacationService.findAllVacations();
        return ResponseEntity.status(HttpStatus.OK).body(vacations);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<VacationResponseDto>> findVacationByUsernameController(@PathVariable(value = "username") String username) {
        User user = AuthenticationUserHolder.get();
        AuthenticationUtils.toValidUserRole(user);
        
        List<VacationResponseDto> vacations = vacationService.findAllVacationForUserName(username);
        return ResponseEntity.status(HttpStatus.OK).body(vacations);

    }

    @PutMapping("/{vacationId}")
    public ResponseEntity<Void> responseRequestVacationController(@PathVariable(value = "vacationId") Long vacationId, @RequestBody 
    VacationUpdateRequestDto vacationUpdateRequestDto) {
        User user = AuthenticationUserHolder.get();
        AuthenticationUtils.toValidUserRole(user);

        EnumTypeVacationStatus status = EnumTypeVacationStatus.valueOf(vacationUpdateRequestDto.status());
        vacationService.responseRequestVacation(vacationId,status);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
