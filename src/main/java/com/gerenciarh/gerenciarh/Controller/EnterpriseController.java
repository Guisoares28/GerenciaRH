package com.gerenciarh.gerenciarh.Controller;

import com.gerenciarh.gerenciarh.DtosRequest.EnterpriseRequestDto;
import com.gerenciarh.gerenciarh.Services.EnterpriseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enterprise")
@CrossOrigin("*")
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> saveEnterpriseController(@Valid @RequestBody EnterpriseRequestDto enterpriseRequestDto) {
        enterpriseService.createEnterpriseService(enterpriseRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{cnpj}")
    public ResponseEntity<Void> updateEnterpriseController(@Valid @RequestBody EnterpriseRequestDto enterpriseRequestDto,
                                                           @PathVariable(value = "cnpj") String cnpj) {
        enterpriseService.updateEnterpriseService(cnpj, enterpriseRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{cnpj}")
    public ResponseEntity<Void> deleteEnterpriseController(@PathVariable(value = "cnpj") String cnpj) {
        enterpriseService.deleteEnterpriseByCnpjService(cnpj);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
