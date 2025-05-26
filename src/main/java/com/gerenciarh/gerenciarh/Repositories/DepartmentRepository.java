package com.gerenciarh.gerenciarh.Repositories;

import com.gerenciarh.gerenciarh.Models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByNameIgnoreCaseAndEnterprise_Id(String departmentName, Long enterpriseId);

    void deleteByNameIgnoreCaseAndEnterprise_Id(String departmentName, Long enterpriseId);

    List<Department> findAllByEnterprise_Id(Long enterpriseId);
}
