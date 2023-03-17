package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.domain.model.Estado;
import com.joaopedroluz57.devfood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.buscarTodos();
    }
    
    @GetMapping("/{id}") 
    public ResponseEntity<Estado> buscar(@PathVariable Long id) {
        final Estado estado = estadoRepository.buscarPorId(id);

        if(Objects.isNull(estado)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(estado);
    }

}
