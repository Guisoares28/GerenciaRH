package com.gerenciarh.gerenciarh.Controller;

import com.gerenciarh.gerenciarh.DtosResponse.NotificationResponseDto;
import com.gerenciarh.gerenciarh.Models.Notification;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Services.NotificationService;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUserHolder;
import com.gerenciarh.gerenciarh.Utils.NotificationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;


    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping()
    public ResponseEntity<List<NotificationResponseDto>> fetchAllNotificationsController() {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.fetchAllNotifications());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> readNotificationController(@PathVariable(value = "id") Long notificationId) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
