package com.gerenciarh.gerenciarh.Repositories;

import com.gerenciarh.gerenciarh.Models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByuserReceiver_Id(Long userReceiverId);

    Notification findByIdAndUserReceiver_Id(Long notificationId, Long userId);
}
