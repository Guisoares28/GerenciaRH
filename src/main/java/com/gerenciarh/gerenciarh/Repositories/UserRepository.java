package com.gerenciarh.gerenciarh.Repositories;

import com.gerenciarh.gerenciarh.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNicknameAndEnterprise_Id(String nickname, Long enterpriseId);

    List<User> findAllByDepartmentNameIgnoreCaseAndEnterprise_Id(String nomeDepartamento, Long enterpriseId);

    List<User> findAllByEnterprise_Id(Long enterpriseId);

    void deleteByNicknameIgnoreCaseAndEnterprise_Id(String nickname, Long enterpriseId);

    Optional<User> findByNickname(String nickname);

}
