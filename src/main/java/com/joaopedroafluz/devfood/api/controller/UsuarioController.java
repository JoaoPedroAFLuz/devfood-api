package com.joaopedroafluz.devfood.api.controller;

import com.joaopedroafluz.devfood.api.assembler.UsuarioInputDisassembler;
import com.joaopedroafluz.devfood.api.assembler.UsuarioModelAssembler;
import com.joaopedroafluz.devfood.api.model.UsuarioModel;
import com.joaopedroafluz.devfood.api.model.input.SenhaInput;
import com.joaopedroafluz.devfood.api.model.input.UsuarioComSenhaInput;
import com.joaopedroafluz.devfood.api.model.input.UsuarioInput;
import com.joaopedroafluz.devfood.domain.model.Usuario;
import com.joaopedroafluz.devfood.domain.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioModelAssembler usuarioModelAssembler;
    private final UsuarioInputDisassembler usuarioInputDisassembler;

    public UsuarioController(UsuarioService usuarioService,
                             UsuarioModelAssembler usuarioModelAssembler,
                             UsuarioInputDisassembler usuarioInputDisassembler) {
        this.usuarioService = usuarioService;
        this.usuarioModelAssembler = usuarioModelAssembler;
        this.usuarioInputDisassembler = usuarioInputDisassembler;
    }

    @GetMapping
    public List<UsuarioModel> buscarTodos() {
        return usuarioService.buscarTodos().stream()
                .map(usuarioModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{usuarioId}")
    public UsuarioModel buscarPorId(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalharPorId(usuarioId);

        return usuarioModelAssembler.toModel(usuario);
    }

    @PostMapping
    public UsuarioModel cadastrar(@RequestBody @Valid UsuarioComSenhaInput usuarioComSenhaInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioComSenhaInput);

        Usuario usuarioPersistido = usuarioService.salvar(usuario);

        return usuarioModelAssembler.toModel(usuarioPersistido);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuario = usuarioService.buscarOuFalharPorId(usuarioId);

        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuario);

        Usuario usuarioPersistido = usuarioService.salvar(usuario);

        return usuarioModelAssembler.toModel(usuarioPersistido);
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
