package com.gerenciarh.gerenciarh.Controller;

import com.gerenciarh.gerenciarh.DtosRequest.UserLoginRequestDto;
import com.gerenciarh.gerenciarh.DtosResponse.TokenResponseDTO;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Services.AuthService;
import com.gerenciarh.gerenciarh.Services.JwtService;
import com.gerenciarh.gerenciarh.Services.TokenEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(authService.authentication(userLoginRequestDto));
   }
}
