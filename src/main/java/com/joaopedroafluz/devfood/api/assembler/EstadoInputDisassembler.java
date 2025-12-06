package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.model.input.EstadoInput;
import com.joaopedroafluz.devfood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class EstadoInputDisassembler {

    private final ModelMapper modelMapper;

    public Estado toDomainObject(@RequestBody @Valid EstadoInput estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }

}
