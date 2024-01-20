package com.joaopedroluz57.devfood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.joaopedroluz57.devfood.api.model.mixin.CidadeMixin;
import com.joaopedroluz57.devfood.api.model.mixin.CozinhaMixin;
import com.joaopedroluz57.devfood.api.model.mixin.UsuarioMixin;
import com.joaopedroluz57.devfood.domain.model.Cidade;
import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Usuario.class, UsuarioMixin.class);
    }

}
