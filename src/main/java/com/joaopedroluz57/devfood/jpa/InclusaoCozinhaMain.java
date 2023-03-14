package com.joaopedroluz57.devfood.jpa;

import com.joaopedroluz57.devfood.DevfoodApiApplication;
import com.joaopedroluz57.devfood.domain.model.Cozinha;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class InclusaoCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(DevfoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CadastroCozinha cadastro = applicationContext.getBean(CadastroCozinha.class);

        Cozinha cozinhaFrancesa = new Cozinha();
        cozinhaFrancesa.setNome("Francesa");

        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");

        cozinhaFrancesa = cadastro.adicionar(cozinhaFrancesa);
        cozinhaBrasileira = cadastro.adicionar(cozinhaBrasileira);


        System.out.printf("%d - %s\n", cozinhaFrancesa.getId(), cozinhaFrancesa.getNome());
        System.out.printf("%d - %s\n", cozinhaBrasileira.getId(), cozinhaBrasileira.getNome());
    }
}
