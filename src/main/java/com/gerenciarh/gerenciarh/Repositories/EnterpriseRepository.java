package com.gerenciarh.gerenciarh.Repositories;

import com.gerenciarh.gerenciarh.Models.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
    Enterprise findByCnpj(String cnpj);
    void deleteByCnpj(String cnpj);
}
