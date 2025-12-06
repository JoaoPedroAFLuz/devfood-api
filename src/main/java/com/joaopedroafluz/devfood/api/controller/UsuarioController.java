package com.joaopedroafluz.devfood.api.controller;

import com.joaopedroafluz.devfood.api.assembler.UsuarioInputDisassembler;
import com.joaopedroafluz.devfood.api.assembler.UsuarioModelAssembler;
import com.joaopedroafluz.devfood.api.model.UsuarioModel;
import com.joaopedroafluz.devfood.api.model.input.SenhaInput;
import com.joaopedroafluz.devfood.api.model.input.UsuarioComSenhaInput;
import com.joaopedroafluz.devfood.api.model.input.UsuarioInput;
import com.joaopedroafluz.devfood.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioModelAssembler usuarioModelAssembler;
    private final UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    public CollectionModel<UsuarioModel> buscarTodos() {
        final var usuarios = usuarioService.buscarTodos();

        return usuarioModelAssembler.toCollectionModel(usuarios)
                .add(linkTo(UsuarioController.class).withSelfRel());
    }

    @GetMapping("/{usuarioId}")
    public UsuarioModel buscarPorId(@PathVariable Long usuarioId) {
        final var usuario = usuarioService.buscarOuFalharPorId(usuarioId);

        return usuarioModelAssembler.toModel(usuario);
    }

    @PostMapping
    public UsuarioModel cadastrar(@RequestBody @Valid UsuarioComSenhaInput usuarioComSenhaInput) {
        final var usuario = usuarioInputDisassembler.toDomainObject(usuarioComSenhaInput);

        usuarioService.salvar(usuario);

        return usuarioModelAssembler.toModel(usuario);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
        final var usuario = usuarioService.buscarOuFalharPorId(usuarioId);

        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuario);

        usuarioService.salvar(usuario);

        return usuarioModelAssembler.toModel(usuario);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senhaInput) {
        usuarioService.atualizarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long usuarioId) {
        usuarioService.excluir(usuarioId);
    }

}
