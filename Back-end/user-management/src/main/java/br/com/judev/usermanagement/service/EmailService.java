package br.com.judev.usermanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Sends a verification code to the provided email address.
     *
     * @param code  The verification code to send.
     * @param email The recipient's email address.
     */
    public void sendCodeToEmail(String code, String email) {
        String message = "Your verification code is " + code + "\nYour code expires in 10 minutes.";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Welcome to Judev's User Management");
        simpleMailMessage.setText(message);

        sendEmail(simpleMailMessage);
    }

    public void sendWelcomeMessageToNewUser(String email, String name) {
        String message = "Hello " + name + "\n"+
                "\n" +
                "Welcome to Judev User-Management ! We are  happy to have you with us.\n" +
                "\n" +
                "We hope you enjoy our services to the fullest. If you have any questions or suggestions, please do not hesitate to contact us.\n" +
                "\n" +
                "Best regards,\n" +
                "Judev Systems Team";

        SimpleMailMessage emailToSend = new SimpleMailMessage();
        emailToSend.setTo(email);
        emailToSend.setSubject("Welcome to Judev System!");
        emailToSend.setText(message);

        sendEmail(emailToSend);
    }

    /**
     * Sends an email using the configured JavaMailSender.
     *
     * @param data The email message to send.
     */
    public void sendEmail(SimpleMailMessage data) {
        try {
            mailSender.send(data);
        } catch (MailSendException e) {
            throw new MailSendException("ERROR SENDING EMAIL CODE");
        } catch (MailAuthenticationException e) {
            throw new MailAuthenticationException("ERROR AUTHENTICATING MAIL SERVER");
        } catch (MailPreparationException e) {
            throw new MailPreparationException("ERROR DURING EMAIL MESSAGE PREPARATION");
        }
    }

    /**
     * Sends an email to notify the user that their data has been updated.
     *
     * @param email The recipient's email address.
     * @param name  The recipient's name.
     */
    public void sendUpdateDataToEmail(String email, String name) {
        String message = "Hello " + name + ",\n" +
                "\n" +
                "We would like to inform you that your data has been successfully updated in our system.\n" +
                "\n" +
                "If you did not request this update or notice any discrepancies in the information, please " +
                "contact us immediately so we can correct any issues.\n" +
                "\n" +
                "Best regards,\n" +
                "PetControl System Team";

        SimpleMailMessage emailToSend = new SimpleMailMessage();
        emailToSend.setTo(email);
        emailToSend.setSubject("Welcome to PetControl System!");
        emailToSend.setText(message);

        sendEmail(emailToSend);
    }

    public void sendEmailUpdatePasswordSet(String email, String name) {
        String message = "Hello " + name + ",\n" +
                "\n" +
                "We would like to inform you that your password has been successfully updated in our system.\n" +
                "\n" +
                "If you did not request this update or notice any discrepancies in the information, please " +
                "contact us immediately so we can correct any issues.\n" +
                "\n" +
                "Best regards,\n" +
                "PetControl System Team";

        SimpleMailMessage emailToSend = new SimpleMailMessage();
        emailToSend.setTo(email);
        emailToSend.setSubject("Welcome to PetControl System!");
        emailToSend.setText(message);

        sendEmail(emailToSend);
    }
}
