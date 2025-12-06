package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.model.input.CidadeInput;
import com.joaopedroafluz.devfood.domain.model.Cidade;
import com.joaopedroafluz.devfood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CidadeInputDisassembler {

    private final ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {
        cidade.setEstado(new Estado());

        modelMapper.map(cidadeInput, cidade);
    }

}
