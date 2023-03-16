package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;


    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.salvar(cozinha);
    }

}
