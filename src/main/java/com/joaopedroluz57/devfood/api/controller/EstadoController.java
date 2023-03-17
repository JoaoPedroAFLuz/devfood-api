package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.domain.model.Estado;
import com.joaopedroluz57.devfood.domain.repository.EstadoRepository;
import com.joaopedroluz57.devfood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService estadoService;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> buscar(@PathVariable Long id) {
        final Estado estado = estadoRepository.buscarPorId(id);

        if (Objects.isNull(estado)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(estado);
    }

    @PostMapping
    public ResponseEntity<Estado> adicionar(@RequestBody Estado estado) {
        estado = estadoService.salvar(estado);

        return ResponseEntity.status(HttpStatus.CREATED).body(estado);
    }

}
