package com.gerenciarh.gerenciarh.Utils;

import java.util.List;
import com.gerenciarh.gerenciarh.DtosRequest.UserRequestDto;
import com.gerenciarh.gerenciarh.DtosResponse.UserResponseDto;
import com.gerenciarh.gerenciarh.Models.User;


public class UserUtils {

    public static User fromDtoForModel(UserRequestDto userRequestDto) {

        User user = new User();

        user.setName(userRequestDto.name());
        user.setContractDate(userRequestDto.contractDate());
        user.setNickname(userRequestDto.nickname());
        user.setPassword(userRequestDto.password());
        user.setCpf(userRequestDto.cpf());
        user.setSalario(userRequestDto.salario());
        user.setEmail(userRequestDto.email());
        user.setCargo(userRequestDto.cargo());
        user.setRole(userRequestDto.role());
        
        return user;
    }

    public static List<UserResponseDto> fromListModelForResponse(List<User> users) {
        return users.stream()
        .filter(user -> user.getDepartment() != null)
        .map(user -> new UserResponseDto(user.getName(),user.getNickname(),user.getPassword(),
        user.getContractDate(), user.getCpf(), user.getSalario(), user.getEmail(), user.getCargo(), user.getDepartment().getname())
        ).toList();
    }

    public static UserResponseDto fromModelFromResponse(User user) {
        return new UserResponseDto(
            user.getName(),
            user.getNickname(),
            user.getPassword(),
            user.getContractDate(),
            user.getCpf(),
            user.getSalario(),user.getEmail(),
            user.getCargo(),
            user.getDepartment().getname()
        );

    }


}
