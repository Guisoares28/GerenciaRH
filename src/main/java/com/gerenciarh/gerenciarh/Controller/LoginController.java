package com.gerenciarh.gerenciarh.Controller;

import com.gerenciarh.gerenciarh.DtosRequest.UserLoginRequestDto;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Services.AuthService;
import com.gerenciarh.gerenciarh.Services.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class LoginController {

    private final AuthService authService;

    private final JwtService jwtService;

    public LoginController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto){
        User user = authService.loadUser(userLoginRequestDto);
        String token = jwtService.gerarToken(user);
        return ResponseEntity.ok(token);
   }
}
