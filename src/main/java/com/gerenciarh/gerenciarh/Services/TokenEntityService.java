package com.gerenciarh.gerenciarh.Services;

import com.gerenciarh.gerenciarh.DtosRequest.TokenUpdateRequestDTO;
import com.gerenciarh.gerenciarh.DtosResponse.TokenResponseDTO;
import com.gerenciarh.gerenciarh.Exceptions.NotFoundException;
import com.gerenciarh.gerenciarh.Models.TokenEntity;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Repositories.TokenRepository;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUserHolder;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenEntityService {

    private TokenRepository tokenRepository;

    private ModelMapper mapper = new ModelMapper();

    public TokenEntityService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void saveToken(TokenEntity token) {
        tokenRepository.save(token);
    }

    public List<TokenResponseDTO> getAllTokens() {
        User user = AuthenticationUserHolder.get();
        List<TokenEntity> tokens = tokenRepository.findAllByUser_Id(user.getId());
        return mapper.map(tokens,new TypeToken<List<TokenResponseDTO>>() {}.getType());
    }

    public TokenEntity getToken() {
        User user = AuthenticationUserHolder.get();
        tokenRepository.findByUser_IdAndStatusTrue(user.getId());
    }

    public void breakToken(String token) {
        User user = AuthenticationUserHolder.get();

        TokenEntity newToken = tokenRepository.findByTokenAndUser_Id(token, user.getId())
                .orElseThrow(() -> new NotFoundException("Token não encontrado"));

        newToken.setStatus(false);
        tokenRepository.save(newToken);
    }

    public boolean verifyExistsActiveTokens(User user) {
        return tokenRepository.existsByUser_IdAndStatusTrue(user.getId());
    }
}
