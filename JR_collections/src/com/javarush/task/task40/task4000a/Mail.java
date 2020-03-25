package com.javarush.task.task40.task4000a;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Mail {

    static Properties properties;
    static Session mailSession;
    static MimeMessage generateMailMessage;

    public static void main(String[] args) throws AddressException, MessagingException, IOException {
        generateAndSendEmail();
        System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
    }

    public static void generateAndSendEmail() throws AddressException, MessagingException, IOException {
        // Step1
        System.out.println("\n 1st ===> setup Mail Server Properties..");
        properties = new Properties();
        // проперти лежат в паке с классом
        final String path = "./4.JavaCollections/src/" + Mail.class.getPackage().getName().replaceAll("\\.", "/") + "/mail.properties";
        FileInputStream in = new FileInputStream(path);
        properties.load(in);

        // properties.load(Mail.class.getResourceAsStream("mail.properties")); <-- если проперти лежат в общей папки проекта
        System.out.println("Mail Server Properties have been setup successfully..");

        // Step2
        System.out.println("\n\n 2nd ===> get Mail Session..");
        mailSession = Session.getDefaultInstance(properties, null);
        generateMailMessage = new MimeMessage(mailSession);
        generateMailMessage.setFrom(new InternetAddress("bum6lebee"));
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("bum6le6ee@yandex.ru"));
        generateMailMessage.setSubject("Greetings from ShT..");
        String emailBody = "Test email by ShT51 JavaMail API example. " + "<br><br> Regards, <br>ShT";
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully..");

        // Step3
        System.out.println("\n\n 3rd ===> Get Session and Send mail");
        Transport transport = mailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com", "bum6lebee@gmail.com", "Google55word!");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}

