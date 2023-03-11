package com.joaopedroluz57.devfood.jpa;

import com.joaopedroluz57.devfood.DevfoodApiApplication;
import com.joaopedroluz57.devfood.domain.model.Cozinha;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(DevfoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CadastroCozinha cadastro = applicationContext.getBean(CadastroCozinha.class);

        List<Cozinha> cozinhas = cadastro.listar();

        for(Cozinha cozinha: cozinhas) {
            System.out.println(cozinha.getNome());
        }
    }
}
