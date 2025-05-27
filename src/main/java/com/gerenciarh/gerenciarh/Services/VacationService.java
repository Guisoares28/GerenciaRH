package com.gerenciarh.gerenciarh.Services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.gerenciarh.gerenciarh.DtosRequest.VacationRequestDto;
import com.gerenciarh.gerenciarh.DtosResponse.VacationResponseDto;
import com.gerenciarh.gerenciarh.Enums.EnumTypeVacationStatus;
import com.gerenciarh.gerenciarh.Exceptions.NotFoundException;
import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Models.Vacation;
import com.gerenciarh.gerenciarh.Repositories.UserRepository;
import com.gerenciarh.gerenciarh.Repositories.VacationRepository;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUserHolder;
import com.gerenciarh.gerenciarh.Utils.AuthenticationUtils;
import com.gerenciarh.gerenciarh.Utils.VacationUtils;

import jakarta.transaction.Transactional;


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

    @Transactional
    public void requestVacation(VacationRequestDto vacationRequestDto) {

        User user = AuthenticationUserHolder.get();
        Vacation requestVacation = VacationUtils.fromRequestForDto(vacationRequestDto);
        requestVacation.setUser(user);
        vacationRepository.save(requestVacation);

        String description = "Usuário " + user.getName() + " Enviou uma solicitação de férias";

        notificationService.sendNotificationToRh(user, description);
    }

    public List<VacationResponseDto> findAllVacations() {
        User user = AuthenticationUserHolder.get();
        List<Vacation> vacations = vacationRepository.findAllByUser_Enterprise_Id(user.getEnterprise().getId());
        return VacationUtils.listVacationModelFromListDtoResponse(vacations);
    }

    public void responseRequestVacation(Long vacationId, EnumTypeVacationStatus status) {
        User user = AuthenticationUserHolder.get();
        AuthenticationUtils.toValidUserRole(user);

        Vacation vacation = vacationRepository.findById(vacationId).orElseThrow(() -> new NotFoundException("Vacation not found"));
        vacation.setStatus(status);

        User userReceiver = vacation.getUser();

        String description = "Usuário " + user.getNickname() + "Mudou o status do seu pedido para " + status;

        notificationService.sendNotification(user, description, userReceiver );
    }

    public List<VacationResponseDto> findAllVacationForUserName (String username) {
        User user = AuthenticationUserHolder.get();

        List<Vacation> vacations = vacationRepository.findAllByUser_NameContainingIgnoreCaseAndUser_Enterprise_Id(username,
         user.getEnterprise().getId());

        return VacationUtils.listVacationModelFromListDtoResponse(vacations);
    }

}
