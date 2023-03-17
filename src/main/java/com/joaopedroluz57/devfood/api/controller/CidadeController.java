package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.domain.exception.EntidadeNaoEncontradaException;
import com.joaopedroluz57.devfood.domain.model.Cidade;
import com.joaopedroluz57.devfood.domain.repository.CidadeRepository;
import com.joaopedroluz57.devfood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cidadeService;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.buscarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long id) {
        final Cidade cidade = cidadeRepository.buscarPorId(id);

        if (Objects.isNull(cidade)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(cidade);
    }

    @GetMapping("/por-estado")
    public List<Cidade> buscarPorEstado(@RequestParam("estadoId") Long estadoId) {
        return cidadeRepository.buscarPorEstadoId(estadoId);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
        try {
            cidade = cidadeService.salvar(cidade);

            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        try {
            Cidade cidadeAtual = cidadeRepository.buscarPorId(id);

            if (Objects.isNull(cidadeAtual)) {
                return ResponseEntity.notFound().build();
            }

            BeanUtils.copyProperties(cidade, cidadeAtual, "id");

            cidadeAtual = cidadeService.salvar(cidadeAtual);

            return ResponseEntity.ok().body(cidadeAtual);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        try {
            cidadeService.excluir(id);

            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
