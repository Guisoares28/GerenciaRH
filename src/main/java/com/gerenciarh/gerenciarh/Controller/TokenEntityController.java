package com.gerenciarh.gerenciarh.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerenciarh.gerenciarh.DtosResponse.TokenResponseDTO;
import com.gerenciarh.gerenciarh.DtosResponse.UserResponseDto;
import com.gerenciarh.gerenciarh.Services.TokenEntityService;

@RestController
@RequestMapping("/tokens")
public class TokenEntityController {

    private final TokenEntityService tokenEntityService;

    public TokenEntityController(TokenEntityService tokenEntityService) {
        this.tokenEntityService = tokenEntityService;
    }

    @GetMapping()
    public ResponseEntity<List<TokenResponseDTO>> findAllTokensController() {
        return ResponseEntity.status(HttpStatus.OK).body(tokenEntityService.getAllTokens());
    }

    @PutMapping
    public ResponseEntity<Void> updateTokenController(@RequestHeader("Authorization") String bearerToken) {
    	String token = bearerToken.replace("Bearer ", "");
    	tokenEntityService.breakToken(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
}
