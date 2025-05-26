package com.gerenciarh.gerenciarh.Services;

import com.gerenciarh.gerenciarh.DtosRequest.UserLoginRequestDto;
import com.gerenciarh.gerenciarh.Exceptions.UserOrPasswordInvalidException;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Repositories.UserRepository;
import com.gerenciarh.gerenciarh.Utils.PasswordUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loadUser(UserLoginRequestDto userLoginRequestDto){
        User user = userRepository.findByNickname(userLoginRequestDto.nickname());
        if(!PasswordUtils.verifySenha(user.getPassword(),userLoginRequestDto.password())){
            throw new UserOrPasswordInvalidException();
        }else{
            return user;
        }
    }
}
