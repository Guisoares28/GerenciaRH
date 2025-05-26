package com.gerenciarh.gerenciarh.Services;


import com.gerenciarh.gerenciarh.DtosRequest.VacationRequestDto;
import com.gerenciarh.gerenciarh.Enums.EnumTypeVacationStatus;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Models.Vacation;
import com.gerenciarh.gerenciarh.Repositories.UserRepository;
import com.gerenciarh.gerenciarh.Repositories.VacationRepository;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUserHolder;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUtils;
import com.gerenciarh.gerenciarh.Utils.VacationUtils;
import org.springframework.stereotype.Service;


@Service
public class VacationService {

    private final VacationRepository vacationRepository;

    private final NotificationService notificationService;

    private final UserRepository userRepository;

    public VacationService(VacationRepository vacationRepository, NotificationService notificationService, UserRepository userRepository) {
        this.vacationRepository = vacationRepository;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    public void requestVacation(VacationRequestDto vacationRequestDto) {

        User user = AuthenticationUserHolder.get();
        Vacation requestVacation = VacationUtils.fromRequestForDto(vacationRequestDto);
        requestVacation.setUser(user);
        vacationRepository.save(requestVacation);

        String description = "Usuário " + user.getName() + " Enviou uma solicitação de férias";

        notificationService.sendNotificationToRh(user, description);
    }

    public void responseRequestVacation(String nickname, EnumTypeVacationStatus status) {
        User user = AuthenticationUserHolder.get();
        AuthenticationUtils.toValidUserRole(user);

        Vacation vacation = vacationRepository.findByUser_nickname(nickname);
        vacation.setStatus(status);

        User userReceiver = userRepository.findByNickname(nickname);

        String description = "Usuário " + user.getNickname() + "Mudou o status do seu pedido para " + status;

        notificationService.sendNotification(user, description, userReceiver );
    }





}
