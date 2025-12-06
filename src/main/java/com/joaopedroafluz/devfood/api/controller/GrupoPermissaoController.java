package com.joaopedroafluz.devfood.api.controller;

import com.joaopedroafluz.devfood.api.assembler.PermissaoModelAssembler;
import com.joaopedroafluz.devfood.api.model.PermissaoModel;
import com.joaopedroafluz.devfood.domain.service.GrupoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    private final GrupoService grupoService;
    private final PermissaoModelAssembler permissaoModelAssembler;

    public GrupoPermissaoController(GrupoService grupoService, PermissaoModelAssembler permissaoModelAssembler) {
        this.grupoService = grupoService;
        this.permissaoModelAssembler = permissaoModelAssembler;
    }


    @GetMapping
    public List<PermissaoModel> buscarTodas(@PathVariable Long grupoId) {
        return grupoService.buscarOuFalharPorId(grupoId).getPermissoes().stream()
                .map(permissaoModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
    }

}
