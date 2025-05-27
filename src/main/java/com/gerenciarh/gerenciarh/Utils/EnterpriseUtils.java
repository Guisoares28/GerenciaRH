package com.gerenciarh.gerenciarh.Utils;

import com.gerenciarh.gerenciarh.DtosRequest.EnterpriseRequestDto;
import com.gerenciarh.gerenciarh.Exceptions.EnterpriseException;
import com.gerenciarh.gerenciarh.Models.Enterprise;

public class EnterpriseUtils {


    public static Enterprise fromEnterpriseDtoForModel(EnterpriseRequestDto enterpriseRequestDto) {
        try{
            return new Enterprise(
                    enterpriseRequestDto.name(),
                    enterpriseRequestDto.cnpj(),
                    enterpriseRequestDto.phone(),
                    enterpriseRequestDto.email()
            );
        }catch (Exception ex){
            throw new EnterpriseException("Não foi possível converter a empresa para Model");
        }
    }

}
