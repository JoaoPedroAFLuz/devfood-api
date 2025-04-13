package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.EnderecoModel;
import com.joaopedroluz57.devfood.domain.model.Endereco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnderecoModelAssembler {

    private final CidadeModelAssembler cidadeModelAssembler;

    public EnderecoModel toModel(Endereco endereco) {
        var cidadeModel = cidadeModelAssembler.toModel(endereco.getCidade());

        return EnderecoModel.builder()
                .cep(endereco.getCep())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(cidadeModel)
                .build();
    }

}
