package com.gerenciarh.gerenciarh.Utils;

import com.gerenciarh.gerenciarh.Enums.EnumTypeRole;
import com.gerenciarh.gerenciarh.Exceptions.UnauthorizedException;
import com.gerenciarh.gerenciarh.Models.Enterprise;
import com.gerenciarh.gerenciarh.Models.User;

public class AuthenticationUtils {

    public static void toValidUserRole(User user) {
        
        if(!user.getRole().equals(EnumTypeRole.ADMIN) && !user.getRole().equals(EnumTypeRole.MASTER)){
            throw new UnauthorizedException();
        }
    }


    public static void toValidUserCreateDept(User user) {
        if(!user.getRole().equals(EnumTypeRole.MASTER)) {
            throw new UnauthorizedException();
        }
    }

    public static void toValidUserEnterprise(User user, Enterprise enterprise) {
        try{
            if(!user.getEnterprise().getId().equals(enterprise.getId())){
                throw new UnauthorizedException();
            }
        }catch (Exception ex){
            throw new RuntimeException("Não foi possível validar o usuario");
        }
    }


}
