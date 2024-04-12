package mx.edu.utez.services_clothing_shop.service.email_service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import mx.edu.utez.services_clothing_shop.utils.Structure;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import static mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns.EMAIL_REGEX;

@Service
public class EmailService {
    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Async
    public void sendEmail(String to, String asunt, String title, String mensaje, String boldMessage) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            if (to == null || to.isEmpty()) {
                throw new CustomException("user.email.notnull");
            } else if (!Pattern.matches(EMAIL_REGEX, to)) {
                throw new CustomException("user.email.invalid");
            } else {
                helper.setTo(to);
                helper.setSubject(asunt);
                String html = Structure.email(title, mensaje, boldMessage);

                helper.setText(html, true);
                emailSender.send(message);
            }
        } catch (MessagingException e) {
            Logger.getLogger(EmailService.class.getName()).severe("Error al enviar correo: " + e.getMessage());
        }
    }

    ;


}