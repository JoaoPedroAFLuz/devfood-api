package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.controller.EstadoController;
import com.joaopedroafluz.devfood.api.model.EstadoModel;
import com.joaopedroafluz.devfood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    private final ModelMapper modelMapper;

    public EstadoModelAssembler(ModelMapper modelMapper) {
        super(EstadoController.class, EstadoModel.class);
        this.modelMapper = modelMapper;
    }


    public EstadoModel toModel(Estado estado) {
        final var estadomodel = createModelWithId(estado.getId(), estado);

        modelMapper.map(estado, estadomodel);

        estadomodel.add(linkTo(EstadoController.class).withRel("estados"));

        return estadomodel;
    }

}
