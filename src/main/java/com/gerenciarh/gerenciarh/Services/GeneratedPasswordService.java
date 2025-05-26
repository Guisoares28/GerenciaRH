package com.gerenciarh.gerenciarh.Services;

import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class GeneratedPasswordService {

    public static String generatedPassword() {
        PasswordGenerator generator = new PasswordGenerator();
        String senha = generatedPassword();
        return senha;
    }

}
