package com.gerenciarh.gerenciarh.Services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gerenciarh.gerenciarh.DtosRequest.DepartamentoRequestDto;
import com.gerenciarh.gerenciarh.DtosResponse.DepartamentoDtoResponse;
import com.gerenciarh.gerenciarh.Exceptions.NotFoundException;
import com.gerenciarh.gerenciarh.Exceptions.RepeatDataException;
import com.gerenciarh.gerenciarh.Models.Department;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Repositories.DepartmentRepository;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUserHolder;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUtils;
import com.gerenciarh.gerenciarh.Utils.DepartamentoUtils;

import jakarta.transaction.Transactional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public void createDepartmentService(DepartamentoRequestDto departamentoRequestDto) {
        try{
            User user = AuthenticationUserHolder.get();

            AuthenticationUtils.toValidUserRole(user);

            Department department = new Department(
                departamentoRequestDto.name(),
                user.getEnterprise());
            departmentRepository.save(department);
        }catch(DataIntegrityViolationException ex){
            throw new RepeatDataException();
        } 
    }

    public DepartamentoDtoResponse findDepartmentByNameAndEnterpriseIdService(String departmentName) {
        User user = AuthenticationUserHolder.get();
        AuthenticationUtils.toValidUserRole(user);
        Department department = departmentRepository.findByNameIgnoreCaseAndEnterprise_Id(
                        departmentName,
                        user.getEnterprise().getId())
                                    .orElseThrow(NotFoundException::new);
        return DepartamentoUtils.ModelFromDto(department);
    }

    public List<DepartamentoDtoResponse> findAllDepartmentsService() {
        User user = AuthenticationUserHolder.get();
        AuthenticationUtils.toValidUserRole(user);
        return DepartamentoUtils.listDtoFromModel(
                departmentRepository.findAllByEnterprise_Id(user.getEnterprise().getId())
        );
    }

    @Transactional
    public void updateDepartamento(String name, DepartamentoRequestDto departamentoRequestDto) {
        try{
            User user = AuthenticationUserHolder.get();
            AuthenticationUtils.toValidUserRole(user);

            Department department = departmentRepository.findByNameIgnoreCaseAndEnterprise_Id(
                name.toUpperCase(), user.getEnterprise().getId() ).orElseThrow(() ->
                    new NotFoundException("Department not found with name" + departamentoRequestDto.name()) );

            department.setname(departamentoRequestDto.name().toUpperCase());
            departmentRepository.save(department);
        }catch(DataIntegrityViolationException ex) {
            throw new RepeatDataException();
        }
       
    }

    @Transactional
    public void deleteDepartamentoPorNome(String departamentName){
        User user = AuthenticationUserHolder.get();
        AuthenticationUtils.toValidUserRole(user);
        departmentRepository.deleteByNameIgnoreCaseAndEnterprise_Id(
                departamentName, user.getEnterprise().getId()
        );
    }
}
