package com.joaopedroluz57.devfood.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties("devfood.email")
public class EmailProperties {

    @NotNull
    private String remetente;

    private Implementacao implementacao = Implementacao.FAKE;

    public enum Implementacao {
        SMTP, FAKE
    }
}
