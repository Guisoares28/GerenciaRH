package com.gerenciarh.gerenciarh.Utils;

import com.gerenciarh.gerenciarh.Models.User;

import java.math.BigDecimal;
import java.util.List;


public class RelatoriosUtils {

    public static BigDecimal somarSalarios(List<User> users) {
        return users.stream()
                .map(User::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
