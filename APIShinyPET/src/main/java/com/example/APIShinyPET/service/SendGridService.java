package com.example.APIShinyPET.service;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sendgrid.Email;
import com.sendgrid.Content;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import java.io.IOException;

@Service
public class SendGridService {

    @Value("${sendgrid.api.key}")
    private String apiKey;

    public void enviarEmail(String para, String assunto, String corpoHtml) throws IOException {
        Email from = new Email("shinypet7@gmail.com", "Shiny Pet");
        Email to = new Email(para);
        Content content = new Content("text/html", corpoHtml);
        Mail mail = new Mail(from, assunto, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);
        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody());
    }
}
