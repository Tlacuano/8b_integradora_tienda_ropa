package mx.edu.utez.services_clothing_shop.service.email_service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    public void enviarCorreoRestablecimiento(String destinatario,String asunto, String mensaje) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            if (destinatario == null || destinatario.isEmpty()) {
                throw new CustomException("user.email.notnull");
            } else if (!Pattern.matches(EMAIL_REGEX, destinatario)) {
                throw new CustomException("user.email.invalid");
            } else {
                helper.setTo(destinatario);
                helper.setSubject(asunto);
                helper.setText(mensaje);
                emailSender.send(message);
            }
        } catch (MessagingException e) {
            Logger.getLogger(EmailService.class.getName()).severe("Error al enviar correo: " + e.getMessage());
        }
    }
}
