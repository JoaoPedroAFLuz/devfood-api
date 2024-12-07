package com.joaopedroluz57.devfood.infrastructure.service.email;

import com.joaopedroluz57.devfood.core.email.EmailProperties;
import com.joaopedroluz57.devfood.domain.model.Email;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService {

    public FakeEnvioEmailService(JavaMailSender mailSender,
                                 Configuration freemarkerConfig,
                                 EmailProperties emailProperties) {
        super(mailSender, freemarkerConfig, emailProperties);
    }

    @Override
    public void enviar(Email email) {
        String corpo = processarTemplate(email);

        log.info("[FAKE E-MAIL] Para: {},\nAssunto: {},\nCorpo: {}", email.getDestinatarios(), email.getAssunto(),
                corpo);
    }
}
