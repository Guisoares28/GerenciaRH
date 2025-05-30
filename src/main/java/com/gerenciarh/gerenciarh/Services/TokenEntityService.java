package com.gerenciarh.gerenciarh.Services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gerenciarh.gerenciarh.DtosResponse.TokenResponseDTO;
import com.gerenciarh.gerenciarh.DtosResponse.UserResponseDto;
import com.gerenciarh.gerenciarh.Enums.EnumTypeRole;
import com.gerenciarh.gerenciarh.Exceptions.NotFoundException;
import com.gerenciarh.gerenciarh.Models.TokenEntity;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Repositories.TokenRepository;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUserHolder;

@Service
public class TokenEntityService {

	@Autowired
	private UserService service;
	
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

    public TokenEntity getToken(User user) {
        return tokenRepository.findByUser_IdAndStatusTrue(user.getId());
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
    
    public UserResponseDto getPayload(String token) {
    	DecodedJWT jwtDecoded = JWT.decode(token);
    	String nickname = jwtDecoded.getClaim("sub").asString();
    	UserResponseDto user = service.getUserByNickname(nickname);
        if(user.role() == null) {
            return user = new UserResponseDto(
                    user.name(),
                    user.nickname(),
                    user.password(),
                    user.contractDate(),
                    user.cpf(),
                    user.salario(),
                    user.email(),
                    user.cargo(),
                    user.departamento(),
                    EnumTypeRole.MASTER
                );
        }
    	return user;
    }
}
