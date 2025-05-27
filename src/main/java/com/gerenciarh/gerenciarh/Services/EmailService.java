package com.gerenciarh.gerenciarh.Services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gerenciarh.gerenciarh.Models.User;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService {

    private final String sender = "gerenciarh645@gmail.com";

    @Value("${EMAIL_TOKEN}")
    private final String TOKEN = new String();

    public EmailService() {
    }

    public void sendEmail(String emailTo, User user) throws IOException {
        Email from = new Email(sender);

        String subject = "New user created successfully";

        String body = String.format("Nickname: %s Password: %s",user.getNickname(), user.getPassword());

        Email to = new Email(emailTo);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(TOKEN);
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
