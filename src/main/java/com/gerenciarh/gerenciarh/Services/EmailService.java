package com.gerenciarh.gerenciarh.Services;

import com.gerenciarh.gerenciarh.Models.User;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.token.authentication}")
    private String APY_KEY;

    public EmailService() {
    }

    public void sendEmail(String emailTo, User user) throws IOException {
        Email from = new Email(sender);

        String subject = "New user created successfully";

        String body = String.format("Nickname: %s Password: %s",user.getNickname(), user.getPassword());

        Email to = new Email(emailTo);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(APY_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException ex) {
            throw ex;
        }
    }
}
