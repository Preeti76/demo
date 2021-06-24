/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.service;

import java.io.IOException;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author TOSHIBA R830
 */
@Component
public class SendMail {

     //    final private String sendGridBean.getApiKey() = "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";
//    static String from = "investments@invsta.io";
//    static String fromName = "Mint Asset Management ";

    public void sendMail(String toEmail, String fromEmail, String subject, String htmlMessage) {
        try {
            Response response = null;
            String[] emailArr = toEmail.split(",");
            if (emailArr.length > 1) {
                Mail mail = new Mail();
                Email formEmail = new Email(fromEmail);
                mail.setFrom(formEmail);
                Personalization personalization = new Personalization();
                for (String to : emailArr) {
                    Email to2 = new Email(to);
                    personalization.addTo(to2);
                }
                mail.addPersonalization(personalization);
                mail.setSubject(subject);
                Content content = new Content("text/html", htmlMessage);
                mail.addContent(content);
                SendGrid sg = new SendGrid("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");
                Request request = new Request();
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                response = sg.api(request);
            } else {
                Email from = new Email(fromEmail, "amar@iesl.co");
                Email to = new Email(toEmail);
                Content content = new Content("text/html", htmlMessage);
                Mail mail = new Mail(from, subject, to, content);
                SendGrid sg = new SendGrid("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");
                Request request = new Request();
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                response = sg.api(request);
            }
            LoggerFactory.getLogger(SendMail.class).debug("Status Code", response.getStatusCode());
            LoggerFactory.getLogger(SendMail.class).debug(response.getBody());
            LoggerFactory.getLogger(SendMail.class).debug("Headers Map", response.getHeaders());
        } catch (IOException ex) {
            LoggerFactory.getLogger(SendMail.class).error("", ex);
        }
    }

    
}
