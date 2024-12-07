package com.joaopedroluz57.devfood.core.config;

import com.joaopedroluz57.devfood.api.model.CidadeResumidaModel;
import com.joaopedroluz57.devfood.api.model.EnderecoModel;
import com.joaopedroluz57.devfood.api.model.input.ItemPedidoInput;
import com.joaopedroluz57.devfood.domain.model.Cidade;
import com.joaopedroluz57.devfood.domain.model.Endereco;
import com.joaopedroluz57.devfood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        TypeMap<Endereco, EnderecoModel> enderecoToEnderecoModelTypeMap = modelMapper
                .createTypeMap(Endereco.class, EnderecoModel.class);

        enderecoToEnderecoModelTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));

        modelMapper.typeMap(Cidade.class, CidadeResumidaModel.class)
                .addMapping(src -> src.getEstado().getNome(), CidadeResumidaModel::setEstado);

        return modelMapper;
    }

}
