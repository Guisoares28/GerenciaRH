package com.gerenciarh.gerenciarh.Utils;

import com.gerenciarh.gerenciarh.DtosResponse.NotificationResponseDto;
import com.gerenciarh.gerenciarh.Models.Notification;

import java.util.List;

public class NotificationUtils {

    public static List<NotificationResponseDto> listNotificationModelFromListDto(List<Notification> notifications){
        return notifications.stream()
                .map(n -> new NotificationResponseDto(
                        n.getId(),
                        n.getDescription(),
                        n.isStatus()
                ))
                .toList();
    }

}
