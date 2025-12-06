package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.model.input.RestauranteInput;
import com.joaopedroafluz.devfood.domain.model.Cidade;
import com.joaopedroafluz.devfood.domain.model.Cozinha;
import com.joaopedroafluz.devfood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler {

    private final ModelMapper modelMapper;

    public RestauranteInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
        restaurante.setCozinha(new Cozinha());

        if (restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(restauranteInput, restaurante);
    }

}
