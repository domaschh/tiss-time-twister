package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.PasswordResetToken;
import at.ac.tuwien.sepr.groupphase.backend.repository.PasswordResetRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.lang.invoke.MethodHandles;
import java.time.Instant;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Override
    @Async
    public void sendRegistrationEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@tisstimetwister.com");
        message.setTo(to);
        message.setSubject("Registration Confirmation");
        message.setText("Thank you for registering on our platform.");
        emailSender.send(message);
        System.out.println("Registration message sent successfully");
    }

    @Override
    public void sendPasswordResetEmail(ApplicationUser user, String token, String appUrl) {
        String url = appUrl + "/resetPassword?token=" + token;
        String message = "<p>To reset your password, click the link below:</p><p><a href='" + url + "'>" + url + "</a></p><p>If you did not request a password reset, please ignore this email.</p>";

        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Reset Password");
            helper.setFrom("noreply@tisstimetwister.com");
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send email", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
