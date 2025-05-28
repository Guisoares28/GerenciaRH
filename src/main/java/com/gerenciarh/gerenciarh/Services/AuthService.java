package com.gerenciarh.gerenciarh.Services;

import com.gerenciarh.gerenciarh.DtosRequest.UserLoginRequestDto;
import com.gerenciarh.gerenciarh.DtosResponse.TokenResponseDTO;
import com.gerenciarh.gerenciarh.Exceptions.NotFoundException;
import com.gerenciarh.gerenciarh.Exceptions.UnauthorizedException;
import com.gerenciarh.gerenciarh.Exceptions.UserOrPasswordInvalidException;
import com.gerenciarh.gerenciarh.Models.TokenEntity;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Repositories.UserRepository;
import com.gerenciarh.gerenciarh.Utils.PasswordUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private ModelMapper mapper = new ModelMapper();

    private final TokenEntityService tokenEntityService;

    public AuthService(UserRepository userRepository, JwtService jwtService, TokenEntityService tokenEntityService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.tokenEntityService = tokenEntityService;
    }

   public TokenResponseDTO Authentication(UserLoginRequestDto userLoginRequestDto) {
        User user = userRepository.findByNickname(userLoginRequestDto.nickname())
                .orElseThrow(() -> new NotFoundException("Usuário ou senha incorretos"));

        if(!PasswordUtils.verifySenha(user.getPassword(), userLoginRequestDto.password())){
            throw new UnauthorizedException("Usuário ou senha incorretos");
        }

        TokenEntity tokenEntity;

        if(tokenEntityService.verifyExistsActiveTokens(user)){
            tokenEntity = tokenEntityService.getToken();
        }else{
            tokenEntity = jwtService.gerarToken(user);

            tokenEntityService.saveToken(tokenEntity);
        }

        return mapper.map(tokenEntity, TokenResponseDTO.class);
   }
}
