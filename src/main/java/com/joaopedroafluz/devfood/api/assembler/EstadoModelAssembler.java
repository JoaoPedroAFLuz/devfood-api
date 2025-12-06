package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.model.EstadoModel;
import com.joaopedroafluz.devfood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstadoModelAssembler {

    private final ModelMapper modelMapper;

    public EstadoModel toModel(Estado estado) {
        return modelMapper.map(estado, EstadoModel.class);
    }

}
