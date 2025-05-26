package com.gerenciarh.gerenciarh.Repositories;

import com.gerenciarh.gerenciarh.Enums.EnumTypeVacationStatus;
import com.gerenciarh.gerenciarh.Models.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {

    List<Vacation> findAllByStatus(EnumTypeVacationStatus status);

    Vacation findByUser_nickname(String nickname);

}
