package com.gerenciarh.gerenciarh.Services;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gerenciarh.gerenciarh.DtosRequest.EnterpriseRequestDto;
import com.gerenciarh.gerenciarh.Enums.EnumTypeRole;
import com.gerenciarh.gerenciarh.Exceptions.NotFoundException;
import com.gerenciarh.gerenciarh.Exceptions.RepeatDataException;
import com.gerenciarh.gerenciarh.Exceptions.UnauthorizedException;
import com.gerenciarh.gerenciarh.Models.Enterprise;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Repositories.EnterpriseRepository;
import com.gerenciarh.gerenciarh.Repositories.UserRepository;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUserHolder;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUtils;
import com.gerenciarh.gerenciarh.Utils.EnterpriseUtils;

import jakarta.transaction.Transactional;

@Service
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();

    private final EmailService emailService;

    private final UserService userService;

    public EnterpriseService(EnterpriseRepository enterpriseRepository, UserRepository userRepository,
    EmailService emailService, UserService userService) {
        this.enterpriseRepository = enterpriseRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.userService = userService;
    }

    @Transactional
    public void createEnterpriseService(EnterpriseRequestDto enterpriseRequestDto) {
        try{
            Enterprise newEnterprise = EnterpriseUtils.fromEnterpriseDtoForModel(enterpriseRequestDto);
            enterpriseRepository.save(newEnterprise);
            User user = userService.generatedUserForPrimaryAccess(newEnterprise);
            emailService.sendEmail(
                    newEnterprise.getEmail(), user
            );
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }catch (DataIntegrityViolationException ex){
            throw new RepeatDataException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Enterprise findEnterpriseForCnpjService(String enterpriseCnpj) {
        try{
            return enterpriseRepository.findByCnpj(enterpriseCnpj);
        }catch (Exception ex){
            throw new NotFoundException("Não foi possível buscar a empresa");
        }
    }

    public Enterprise findEnterpriseByIdService(Long id) {
        try{
            return enterpriseRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("não foi encontrado nenhuma empresa com este id"));
        }catch (Exception ex){
            throw new RuntimeException("Não foi possível buscar a empresa");
        }
    }

    public List<Enterprise> findAllEnterprisesService() {
        try{
            return enterpriseRepository.findAll();
        }catch (Exception ex){
            throw new RuntimeException("Não foi possível buscar a empresa");
        }
    }

    @Transactional
    public void updateEnterpriseService(String cnpj, EnterpriseRequestDto enterpriseRequestDto) {
        try{
            User user = AuthenticationUserHolder.get();
            if(!user.getRole().equals(EnumTypeRole.MASTER)){
                throw new UnauthorizedException();
            }
            Enterprise enterprise = this.findEnterpriseForCnpjService(cnpj);
            AuthenticationUtils.toValidUserEnterprise(user, enterprise);
            enterprise.setName(enterpriseRequestDto.name());
            enterprise.setCnpj(enterpriseRequestDto.cnpj());
            enterprise.setPhone(enterpriseRequestDto.phone());
            enterprise.setEmail(enterpriseRequestDto.email());
            enterpriseRepository.save(enterprise);
        }catch (Exception ex){
            throw new RuntimeException("Não foi possível atualizar os dados da empresa");
        }
    }

    @Transactional
    public void deleteEnterpriseByIdService(Long id) {
        try{

            enterpriseRepository.deleteById(id);
        }catch (Exception ex){
            throw new RuntimeException("Não foi possível deletar a empresa");
        }
    }
    @Transactional
    public void deleteEnterpriseByCnpjService(String cnpj) {
        try{
            User user = AuthenticationUserHolder.get();
            if(!user.getRole().equals(EnumTypeRole.MASTER)){
                throw new UnauthorizedException();
            }
            Enterprise enterprise = this.findEnterpriseForCnpjService(cnpj);
            AuthenticationUtils.toValidUserEnterprise(user, enterprise);
            enterpriseRepository.deleteByCnpj(enterprise.getCnpj());
        }catch (Exception ex){
            throw new RuntimeException("Não foi possível deletar a empresa");
        }
    }
}