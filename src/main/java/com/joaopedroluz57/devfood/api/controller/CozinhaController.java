package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.api.controller.model.CozinhasXmlWrapper;
import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping()
    public List<Cozinha> listar() {
        return cozinhaRepository.todos();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml() {
        return new CozinhasXmlWrapper(cozinhaRepository.todos());
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cozinhaRepository.porId(cozinhaId);

        if (Objects.nonNull(cozinha)) {
            return ResponseEntity.ok(cozinha);
        }

        return ResponseEntity.notFound().build();

    }

    @PostMapping()
    public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha) {
        Cozinha cozinhaPersistida = cozinhaRepository.adicionar(cozinha);

        return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaPersistida);
    }

}
