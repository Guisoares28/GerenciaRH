package com.gerenciarh.gerenciarh.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gerenciarh.gerenciarh.Enums.EnumTypeVacationStatus;
import com.gerenciarh.gerenciarh.Models.Vacation;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {

    List<Vacation> findAllByStatus(EnumTypeVacationStatus status);

    Vacation findByUser_nickname(String nickname);

    List<Vacation> findAllByUser_Enterprise_Id(Long id);

    List<Vacation> findAllByUser_NameContainingIgnoreCaseAndUser_Enterprise_Id(String username, Long id);
   

}
