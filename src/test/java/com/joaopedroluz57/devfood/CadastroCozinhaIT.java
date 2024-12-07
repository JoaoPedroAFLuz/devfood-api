package com.joaopedroluz57.devfood;

import com.joaopedroluz57.devfood.domain.exception.CozinhaNaoEncontradaException;
import com.joaopedroluz57.devfood.domain.exception.EntidadeEmUsoException;
import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.service.CozinhaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIT {

    @Autowired
    private CozinhaService cozinhaService;

    @Test
    public void deveAtribuirIdENome_QuandoCadastrarCozinhaComDadosCorretos() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        novaCozinha = cozinhaService.salvar(novaCozinha);

        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
        assertThat(novaCozinha.getNome()).isEqualTo("Chinesa");
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        cozinhaService.salvar(novaCozinha);
    }

    @Test(expected = EntidadeEmUsoException.class)
    public void deveFalhar_QuandoExcluirCozinhaEmUso() {
        cozinhaService.excluir(1L);
    }

    @Test(expected = CozinhaNaoEncontradaException.class)
    public void deveFalhar_QuandoExcluirCozinhaInexistente() {
        cozinhaService.excluir(100L);
    }

}
