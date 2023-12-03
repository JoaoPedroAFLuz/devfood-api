package com.joaopedroluz57.devfood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.joaopedroluz57.devfood.api.model.mixin.RestauranteMixin;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
    }

}
