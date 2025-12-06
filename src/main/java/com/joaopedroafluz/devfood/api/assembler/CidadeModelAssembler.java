package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.controller.CidadeController;
import com.joaopedroafluz.devfood.api.controller.EstadoController;
import com.joaopedroafluz.devfood.api.model.CidadeModel;
import com.joaopedroafluz.devfood.api.model.EstadoModel;
import com.joaopedroafluz.devfood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    private final ModelMapper modelMapper;

    public CidadeModelAssembler(ModelMapper modelMapper) {
        super(CidadeController.class, CidadeModel.class);
        this.modelMapper = modelMapper;
    }


    @Override
    public CidadeModel toModel(Cidade cidade) {
        final var cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        final var estadoModel = modelMapper.map(cidade.getEstado(), EstadoModel.class);

        cidadeModel.setEstado(estadoModel);

        cidadeModel.add(linkTo(methodOn(CidadeController.class).buscarTodas()).withRel("cidades"));
        cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class).buscarPorId(cidadeModel.getEstado().getId())).withSelfRel());

        return cidadeModel;
    }

}
