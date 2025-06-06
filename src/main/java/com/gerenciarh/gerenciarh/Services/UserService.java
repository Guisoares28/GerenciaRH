package com.gerenciarh.gerenciarh.Services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gerenciarh.gerenciarh.DtosRequest.UserRequestDto;
import com.gerenciarh.gerenciarh.DtosResponse.UserResponseDto;
import com.gerenciarh.gerenciarh.Enums.EnumTypeRole;
import com.gerenciarh.gerenciarh.Exceptions.BadRequestException;
import com.gerenciarh.gerenciarh.Exceptions.NotFoundException;
import com.gerenciarh.gerenciarh.Exceptions.RepeatDataException;
import com.gerenciarh.gerenciarh.Exceptions.UnauthorizedException;
import com.gerenciarh.gerenciarh.Models.Department;
import com.gerenciarh.gerenciarh.Models.Enterprise;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Repositories.DepartmentRepository;
import com.gerenciarh.gerenciarh.Repositories.UserRepository;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUserHolder;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUtils;
import com.gerenciarh.gerenciarh.Utils.UserUtils;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final DepartmentRepository departmentRepository;

    public UserService(UserRepository userRepository, DepartmentRepository departmentRepository, TokenEntityService tokenService) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public void createUser(UserRequestDto userRequestDto) {
        try {
            User user = AuthenticationUserHolder.get(); 
            AuthenticationUtils.toValidUserRole(user);

            if(userRequestDto.role().equals(EnumTypeRole.MASTER)){
                if(!user.getRole().equals(EnumTypeRole.MASTER)){
                    throw new UnauthorizedException();
                }
            }

            User newUser = UserUtils.fromDtoForModel(userRequestDto);

            Department department = departmentRepository.findByNameIgnoreCaseAndEnterprise_Id(userRequestDto.departmentName(), user.getEnterprise().getId())
            .orElseThrow(() -> new NotFoundException("Departamento " + userRequestDto.departmentName() + " Não existe"));

            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setDepartment(department);
            newUser.setEnterprise(user.getEnterprise());

            userRepository.save(newUser);
        } catch (DataIntegrityViolationException ex) {
            throw new RepeatDataException();
        }
    }

    public List<UserResponseDto> getAllUsers() {
            User user = AuthenticationUserHolder.get();
            AuthenticationUtils.toValidUserRole(user);

            List<User> users = userRepository.findAllByEnterprise_Id(user.getEnterprise().getId());

            if(users.isEmpty()){
                throw new NotFoundException("Nenhum registro encontrado");
            }

            return UserUtils.fromListModelForResponse(users);
    }

    public UserResponseDto getUserByNickname(String nickname) {

        User user = AuthenticationUserHolder.get();
        User foundUser = userRepository.findByNicknameAndEnterprise_Id(nickname, user.getEnterprise().getId())
            .orElseThrow(() -> new NotFoundException("Usuário com " + nickname + " não encontrado"));
        return UserUtils.fromModelFromResponse(foundUser);
    }

    public List<UserResponseDto> getAllUsersByDepartment(String departmentName) {

        User user = AuthenticationUserHolder.get();
        AuthenticationUtils.toValidUserRole(user);

        return UserUtils.fromListModelForResponse(userRepository
                .findAllByDepartmentNameIgnoreCaseAndEnterprise_Id(departmentName, user.getEnterprise().getId()));
    }

    @Transactional
    public void updateUserByName(String nickname, UserRequestDto userRequestDto) {
        try {

        User authenticatedUser = AuthenticationUserHolder.get();
        AuthenticationUtils.toValidUserRole(authenticatedUser);

        User userUpdate = userRepository.findByNicknameAndEnterprise_Id(nickname, authenticatedUser.getEnterprise().getId())
            .orElseThrow(() -> new NotFoundException("usuário com " + nickname + " não encontrado"));

        userUpdate.setName(userRequestDto.name());
        userUpdate.setContractDate(userRequestDto.contractDate());
        userUpdate.setNickname(userRequestDto.nickname());

        if(!userRequestDto.password().equals(userUpdate.getPassword())){
            userUpdate.setPassword(bCryptPasswordEncoder.encode(userRequestDto.password()));
        }

        userUpdate.setCpf(userRequestDto.cpf());
        userUpdate.setSalario(userRequestDto.salario());
        userUpdate.setEmail(userRequestDto.email());
        userUpdate.setCargo(userRequestDto.cargo());
        userUpdate.setDepartment(departmentRepository.findByNameIgnoreCaseAndEnterprise_Id(userRequestDto.departmentName(),
                authenticatedUser.getEnterprise().getId())
                .orElseThrow(() -> new NotFoundException("Departamento com nome " + userRequestDto.departmentName()
                        + " não encontrado")));
        userRepository.save(userUpdate);
        } catch (DataIntegrityViolationException ex) {
            throw new RepeatDataException();
        }
    }
    
    /* 
    Esse método foi feito com o intuito de receber um token 
    para garantir que o usuário 
    não passe um nickname de outra pessoa no front end e 
    assim altere ele
    */
    @Transactional
    public void updateUserByToken(String token, UserRequestDto user) {
    	User authenticatedUser = AuthenticationUserHolder.get();
        
        // Payload para pegar o nickname
    	UserResponseDto payload = getPayload(token);
    	
    	
        User userUpdate = userRepository.findByNicknameAndEnterprise_Id(payload.nickname(), authenticatedUser.getEnterprise().getId())
                .orElseThrow(() -> new NotFoundException("usuário com " + payload.nickname() + " não encontrado"));
    	
        if (user.name() != null) { userUpdate.setName(user.name());}
        
        if (!user.nickname().equals(userUpdate.getNickname()) || user.nickname() == null || user.nickname().isEmpty() || user.nickname().isBlank()) {
        	throw new BadRequestException("O nickname não pode ser nulo e não pode ser alterado");
        }
        
        if (user.email() != null) userUpdate.setEmail(user.email());
        
    	userRepository.save(userUpdate);
    }
    
    public UserResponseDto getPayload(String token) {
    	DecodedJWT jwtDecoded = JWT.decode(token);
    	String nickname = jwtDecoded.getClaim("sub").asString();
    	UserResponseDto user = getUserByNickname(nickname);
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
    
    @Transactional
    public void deleteUser(String nickname){

        User user = AuthenticationUserHolder.get();
        AuthenticationUtils.toValidUserRole(user);

        userRepository.deleteByNicknameIgnoreCaseAndEnterprise_Id(nickname, user.getEnterprise().getId());
    }

    @Transactional
    public User generatedUserForPrimaryAccess(Enterprise enterprise) {
        User user =  new User();
        String password = GeneratedPasswordService.generatedPassword();
        user.setNickname(enterprise.getCnpj());
        user.setPassword(password);
        user.setEnterprise(enterprise);
        user.setRole(EnumTypeRole.MASTER);
        return user;
    }


}
