package com.joaopedroluz57.devfood.infrastructure.service.email;

import com.joaopedroluz57.devfood.core.email.EmailProperties;
import com.joaopedroluz57.devfood.domain.model.Email;
import com.joaopedroluz57.devfood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
public class SmtpEnvioEmailService implements EnvioEmailService {

    private final JavaMailSender mailSender;
    private final Configuration freemarkerConfig;
    private final EmailProperties emailProperties;

    @Override
    public void enviar(Email email) {
        try {
            MimeMessage mimeMessage = criarMimeMessage(email);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar e-mail", e);
        }
    }

    protected MimeMessage criarMimeMessage(Email email) throws MessagingException {
        String corpo = processarTemplate(email);

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(emailProperties.getRemetente());
        helper.setTo(email.getDestinatarios().toArray(new String[0]));
        helper.setSubject(email.getAssunto());
        helper.setText(corpo, true);

        return mimeMessage;
    }

    protected String processarTemplate(Email email) {
        try {
            Template template = freemarkerConfig.getTemplate(email.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, email.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possível processar o template do e-mail", e);
        }
    }

}
