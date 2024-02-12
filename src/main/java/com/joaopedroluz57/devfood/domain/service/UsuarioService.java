package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.exception.EntidadeEmUsoException;
import com.joaopedroluz57.devfood.domain.exception.NegocioException;
import com.joaopedroluz57.devfood.domain.exception.UsuarioNaoEncontradoException;
import com.joaopedroluz57.devfood.domain.model.Usuario;
import com.joaopedroluz57.devfood.domain.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private static final String MSG_USUARIO_EM_USO = "Usuário com o código %d não pode ser removido, pois está em uso";
    private static final String MSG_USUARIO_JA_EXISTENTE_COM_MESMO_EMAIL = "Já existe um usuário cadastrado com o e-mail %s";

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarOuFalharPorId(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(String.format(MSG_USUARIO_JA_EXISTENTE_COM_MESMO_EMAIL, usuario.getEmail()));
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void atualizarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalharPorId(usuarioId);

        boolean isSenhaCorreta = usuario.getSenha().equals(senhaAtual);

        if (!isSenhaCorreta) {
            throw new NegocioException("Senha atual incorreta");
        }

        usuario.setSenha(novaSenha);
    }

    @Transactional
    public void excluir(Long usuarioId) {
        try {
            usuarioRepository.deleteById(usuarioId);
            usuarioRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(usuarioId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, usuarioId));
        }
    }

}
