package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.controller.CozinhaController;
import com.joaopedroafluz.devfood.api.model.CozinhaModel;
import com.joaopedroafluz.devfood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    private final ModelMapper modelMapper;

    public CozinhaModelAssembler(ModelMapper modelMapper) {
        super(CozinhaController.class, CozinhaModel.class);
        this.modelMapper = modelMapper;
    }


    public CozinhaModel toModel(Cozinha cozinha) {
        final var cozinhaModel = createModelWithId(cozinha.getId(), cozinha);

        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(linkTo(CozinhaController.class).withRel("cozinhas"));

        return cozinhaModel;
    }

}
