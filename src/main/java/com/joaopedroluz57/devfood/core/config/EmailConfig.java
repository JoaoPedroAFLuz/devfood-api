package com.joaopedroluz57.devfood.core.config;

import com.joaopedroluz57.devfood.core.email.EmailProperties;
import com.joaopedroluz57.devfood.domain.service.EnvioEmailService;
import com.joaopedroluz57.devfood.infrastructure.service.email.FakeEnvioEmailService;
import com.joaopedroluz57.devfood.infrastructure.service.email.SandboxEnvioEmailService;
import com.joaopedroluz57.devfood.infrastructure.service.email.SmtpEnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {

    private final EmailProperties emailProperties;
    private final JavaMailSender mailSender;
    private final freemarker.template.Configuration freemarkerConfig;

    @Bean
    public EnvioEmailService envioEmailService() {
        switch (emailProperties.getImplementacao()) {
            case FAKE:
                return new FakeEnvioEmailService(mailSender, freemarkerConfig, emailProperties);
            case SMTP:
                return new SmtpEnvioEmailService(mailSender, freemarkerConfig, emailProperties);
            case SANDBOX:
                return new SandboxEnvioEmailService(mailSender, freemarkerConfig, emailProperties);
            default:
                return null;
        }
    }
}
