package com.gerenciarh.gerenciarh.Services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class GeneratedPasswordService {

    public static String generatedPassword() {
        return RandomStringUtils.randomAlphanumeric(12);
    }

}
