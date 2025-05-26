package com.gerenciarh.gerenciarh.Services;
import com.gerenciarh.gerenciarh.DtosResponse.NotificationResponseDto;
import com.gerenciarh.gerenciarh.Models.Notification;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Repositories.NotificationRepository;
import com.gerenciarh.gerenciarh.Repositories.UserRepository;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUserHolder;
import com.gerenciarh.gerenciarh.Utils.NotificationUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;



    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void sendNotificationToRh(User authUser, String description) {
        List<User> usersRh = userRepository.findAllByDepartmentNameIgnoreCaseAndEnterprise_Id("RH",
                authUser.getEnterprise().getId());

        for(User user: usersRh) {
            Notification notification = new Notification();
            notification.setDescription(description);
            notification.setUserSender(authUser);
            notification.setUserReceiver(user);
            notificationRepository.save(notification);
        }

    }

    public void sendNotification(User authUser, String description, User userReceiver) {
        Notification notification = new Notification();
        notification.setDescription(description);
        notification.setUserSender(authUser);
        notification.setUserReceiver(userReceiver);
        notificationRepository.save(notification);
    }

    public List<NotificationResponseDto> fetchAllNotifications() {
        User user = AuthenticationUserHolder.get();
        return NotificationUtils.listNotificationModelFromListDto(
               notificationRepository.findAllByuserReceiver_Id(user.getId())
       );
    }

    public void readNotification(Long notificationId) {
        User user = AuthenticationUserHolder.get();

        Notification notification = notificationRepository.findByIdAndUserReceiver_Id(notificationId, user.getId());

        notification.setStatus(true);
    }



}
