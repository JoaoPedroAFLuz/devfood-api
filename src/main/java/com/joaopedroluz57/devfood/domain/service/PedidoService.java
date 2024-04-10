package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.model.Pedido;
import com.joaopedroluz57.devfood.domain.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }


    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }

}

