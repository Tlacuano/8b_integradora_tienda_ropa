package mx.edu.utez.services_clothing_shop.service.email_service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;
    public void enviarCorreoRestablecimiento(String destinatario, String mensaje) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(destinatario);
            helper.setSubject("Si se puedeee!!!");
            helper.setText("Esta es una prueba de las buenas: "+mensaje);
            emailSender.send(message);
        } catch (MessagingException e) {
            // Manejo de errores al enviar el correo electrónico
            e.printStackTrace();

        }
    }
}
