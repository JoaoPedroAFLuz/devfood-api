package com.joaopedroluz57.devfood.jpa;

import com.joaopedroluz57.devfood.DevfoodApiApplication;
import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class AlteracaoCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(DevfoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);

        Cozinha cozinha1 = new Cozinha();

        cozinha1.setId(1L);
        cozinha1.setNome("Japonesa");

        cozinhas.salvar(cozinha1);
    }
}
