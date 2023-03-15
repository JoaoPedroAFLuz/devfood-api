package com.joaopedroluz57.devfood.jpa;

import com.joaopedroluz57.devfood.DevfoodApiApplication;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import com.joaopedroluz57.devfood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaRestauranteMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(DevfoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRepository restaurantes = applicationContext.getBean(RestauranteRepository.class);

        List<Restaurante> todasRestaurantes = restaurantes.todos();

        for (Restaurante restaurante : todasRestaurantes) {
            System.out.printf("%s - %f - %s\n",
                    restaurante.getNome(), restaurante.getTaxaEntrega(), restaurante.getCozinha().getNome()
            );
        }
    }
}
