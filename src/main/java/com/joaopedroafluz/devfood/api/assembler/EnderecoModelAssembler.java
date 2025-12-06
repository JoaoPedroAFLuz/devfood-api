package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.model.EnderecoModel;
import com.joaopedroafluz.devfood.domain.model.Endereco;
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
