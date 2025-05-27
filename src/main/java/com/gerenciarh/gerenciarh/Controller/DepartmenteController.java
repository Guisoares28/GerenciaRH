package com.gerenciarh.gerenciarh.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gerenciarh.gerenciarh.DtosRequest.DepartamentoRequestDto;
import com.gerenciarh.gerenciarh.DtosResponse.DepartamentoDtoResponse;
import com.gerenciarh.gerenciarh.Services.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/department")
@CrossOrigin("*")
public class DepartmenteController {

    private final DepartmentService departmentService;

    public DepartmenteController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createDepartment(@Valid @RequestBody DepartamentoRequestDto departamentoRequestDto){
        departmentService.createDepartmentService(departamentoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public ResponseEntity<DepartamentoDtoResponse> buscarDepartamentoPorNome(@RequestParam(value = "dptName")
                                                                                 String departmentName) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService
                .findDepartmentByNameAndEnterpriseIdService(departmentName));
    }

    @PutMapping("/{departmentName}")
    public ResponseEntity<Void> updateDepartamento(@Valid @PathVariable(value = "departmentName")
                                                     String departmentName,
                                                     @RequestBody DepartamentoRequestDto departamentoRequestDto) {
        departmentService.updateDepartamento(departmentName, departamentoRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{departmentName}")
    public ResponseEntity<Void> deletarDepartamentoPorNome(@PathVariable(value = "departmentName") String departamentName) {
        departmentService.deleteDepartamentoPorNome(departamentName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }




}
