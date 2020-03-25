package com.javarush.task.task40.task4003;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/* 
Отправка письма с файлом
*/

public class Solution {
    private final String contentPath = "D:\\Yandex Disk\\YandexDisk\\Coding\\Compilation\\Java\\JavaRush\\JavaRushTasks\\4.JavaCollections\\src\\com\\javarush\\task\\task40\\task4003\\description.txt";

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.sendMail("bum6lebee@gmail.com", "Google55word!", "bum6le6ee@yandex.ru");
    }

    public void sendMail(final String username, final String password, final String recipients) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // создаем пустой объект MineMessage
            Message message = new MimeMessage(session);
            // создаем заголовок : "Отправлено от"
            message.setFrom(new InternetAddress(username));
            // указываем куда его отправлем
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));

            // создаем "тему" письма
            setSubject(message, "Тестовое письмо");

            setAttachment(message, contentPath);

            Transport.send(message);
            System.out.println("Письмо было отправлено.");

        } catch (MessagingException e) {
            System.out.println("Ошибка при отправке: " + e.toString());
        }
    }

    public static void setSubject(Message message, String subject) throws MessagingException {
        message.setSubject(subject);
    }

    public static void setAttachment(Message message, String filename) throws MessagingException {
        // Create a multipar message
        Multipart multipart = new MimeMultipart();
        BodyPart messageBodyPart = new MimeBodyPart();

        //Set File
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);

        //Add "file part" to multipart
        multipart.addBodyPart(messageBodyPart);

        //Set multipart to message
        message.setContent(multipart);
    }
}