package com.gerenciarh.gerenciarh.Controller;

import com.gerenciarh.gerenciarh.DtosRequest.TokenUpdateRequestDTO;
import com.gerenciarh.gerenciarh.DtosResponse.TokenResponseDTO;
import com.gerenciarh.gerenciarh.Services.TokenEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{token}")
    public ResponseEntity<Void> updateTokenController(@PathVariable(value = "token") String token){
        tokenEntityService.breakToken(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
