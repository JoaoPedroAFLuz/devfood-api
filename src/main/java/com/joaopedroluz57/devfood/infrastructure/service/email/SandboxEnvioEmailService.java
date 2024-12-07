package com.joaopedroluz57.devfood.infrastructure.service.email;

import com.joaopedroluz57.devfood.core.email.EmailProperties;
import com.joaopedroluz57.devfood.domain.model.Email;
import freemarker.template.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SandboxEnvioEmailService extends SmtpEnvioEmailService {

    private final EmailProperties emailProperties;

    public SandboxEnvioEmailService(JavaMailSender mailSender,
                                    Configuration freemarkerConfig,
                                    EmailProperties emailProperties) {
        super(mailSender, freemarkerConfig, emailProperties);
        this.emailProperties = emailProperties;
    }

    @Override
    protected MimeMessage criarMimeMessage(Email email) throws MessagingException {
        MimeMessage mimeMessage = super.criarMimeMessage(email);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(emailProperties.getSandbox().getDestinatario());

        return mimeMessage;
    }
}