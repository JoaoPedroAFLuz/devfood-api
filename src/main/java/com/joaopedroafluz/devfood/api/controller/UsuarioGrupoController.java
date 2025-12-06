package com.joaopedroafluz.devfood.api.controller;

import com.joaopedroafluz.devfood.api.assembler.GrupoModelAssembler;
import com.joaopedroafluz.devfood.api.model.GrupoModel;
import com.joaopedroafluz.devfood.domain.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<GrupoModel> buscarTodos(@PathVariable Long usuarioId) {
        return usuarioService.buscarOuFalharPorId(usuarioId).getGrupos().stream()
                .map(grupoModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.associarGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desassociarGrupo(usuarioId, grupoId);
    }

}
