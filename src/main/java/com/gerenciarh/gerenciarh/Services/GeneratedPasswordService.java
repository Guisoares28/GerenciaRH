package com.gerenciarh.gerenciarh.Services;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class GeneratedPasswordService {

    public static String generatedPassword() {
        return UUID.randomUUID().toString().substring(0, 12);
    }

}
