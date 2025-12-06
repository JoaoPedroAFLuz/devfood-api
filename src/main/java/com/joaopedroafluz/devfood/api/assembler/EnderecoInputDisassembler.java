package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.model.input.EnderecoInput;
import com.joaopedroafluz.devfood.domain.model.Cidade;
import com.joaopedroafluz.devfood.domain.model.Endereco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnderecoInputDisassembler {


    public Endereco toDomainObject(EnderecoInput enderecoInput) {
        var cidade = new Cidade();
        cidade.setId(enderecoInput.getCidade().getId());

        return Endereco.builder()
                .cep(enderecoInput.getCep())
                .logradouro(enderecoInput.getLogradouro())
                .numero(enderecoInput.getNumero())
                .complemento(enderecoInput.getComplemento())
                .bairro(enderecoInput.getBairro())
                .cidade(cidade)
                .build();
    }

}
