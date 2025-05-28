package com.gerenciarh.gerenciarh.Repositories;

import com.gerenciarh.gerenciarh.Models.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    List<TokenEntity> findAllByUser_Id(Long userId);

    Optional<TokenEntity> findByTokenAndUser_Id(String token, Long userId);
}
