package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.controller.UsuarioController;
import com.joaopedroafluz.devfood.api.controller.UsuarioGrupoController;
import com.joaopedroafluz.devfood.api.model.UsuarioModel;
import com.joaopedroafluz.devfood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    private final ModelMapper modelMapper;

    public UsuarioModelAssembler(ModelMapper modelMapper) {
        super(UsuarioController.class, UsuarioModel.class);
        this.modelMapper = modelMapper;
    }

    public UsuarioModel toModel(Usuario usuario) {
        final var usuarioModel = createModelWithId(usuario.getId(), usuario);

        modelMapper.map(usuario, usuarioModel);

        usuarioModel.add(linkTo(methodOn(UsuarioController.class).buscarTodos()).withRel("usuarios"));
        usuarioModel.add(linkTo(methodOn(UsuarioGrupoController.class).buscarTodos(usuarioModel.getId())).withRel("grupos-usuario"));

        return usuarioModel;
    }

}
