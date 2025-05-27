package com.gerenciarh.gerenciarh.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerenciarh.gerenciarh.DtosResponse.NotificationResponseDto;
import com.gerenciarh.gerenciarh.Services.NotificationService;

@RestController
@RequestMapping("/notification")
@CrossOrigin("*")
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
