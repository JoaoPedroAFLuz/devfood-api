package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.api.controller.model.CozinhasXmlWrapper;
import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.todos();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml() {
        return new CozinhasXmlWrapper(cozinhaRepository.todos());
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cozinhaRepository.buscarPorId(cozinhaId);

        if (Objects.isNull(cozinha)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cozinha);
    }

    @PostMapping
    public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha) {
        Cozinha cozinhaPersistida = cozinhaRepository.adicionar(cozinha);

        return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaPersistida);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaAtual = cozinhaRepository.buscarPorId(id);

        if (Objects.isNull(cozinhaAtual)) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

        cozinhaAtual = cozinhaRepository.adicionar(cozinhaAtual);

        return ResponseEntity.ok(cozinhaAtual);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        try {
            Cozinha cozinha = cozinhaRepository.buscarPorId(id);

            if (Objects.isNull(cozinha)) {
                return ResponseEntity.notFound().build();
            }

            cozinhaRepository.remover(cozinha);

            return ResponseEntity.noContent().build();

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
