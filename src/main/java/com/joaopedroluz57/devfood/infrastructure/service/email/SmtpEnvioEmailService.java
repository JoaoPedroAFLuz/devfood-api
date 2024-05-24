package com.joaopedroluz57.devfood.infrastructure.service.email;

import com.joaopedroluz57.devfood.core.email.EmailProperties;
import com.joaopedroluz57.devfood.domain.model.Email;
import com.joaopedroluz57.devfood.domain.service.EnvioEmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;

    public SmtpEnvioEmailService(JavaMailSender javaMailSender,
                                 EmailProperties emailProperties) {
        this.mailSender = javaMailSender;
        this.emailProperties = emailProperties;
    }


    @Override
    public void enviar(Email email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(email.getDestinatarios().toArray(new String[0]));
            helper.setSubject(email.getAssunto());
            helper.setText(email.getCorpo(), true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar o e-mail", e);
        }
    }

}
