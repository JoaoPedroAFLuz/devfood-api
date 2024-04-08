package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.api.assembler.GrupoModelAssembler;
import com.joaopedroluz57.devfood.api.model.GrupoModel;
import com.joaopedroluz57.devfood.domain.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    private final UsuarioService usuarioService;
    private final GrupoModelAssembler grupoModelAssembler;

    public UsuarioGrupoController(UsuarioService usuarioService, GrupoModelAssembler grupoModelAssembler) {
        this.usuarioService = usuarioService;
        this.grupoModelAssembler = grupoModelAssembler;
    }


    @GetMapping
    public List<GrupoModel> listar(@PathVariable Long usuarioId) {
        return usuarioService.buscarOuFalharPorId(usuarioId).getGrupos().stream()
                .map(grupoModelAssembler::toModel)
                .collect(Collectors.toList());
    }

}
