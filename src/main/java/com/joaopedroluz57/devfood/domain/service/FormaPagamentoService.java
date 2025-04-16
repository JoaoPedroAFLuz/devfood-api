package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.exception.EntidadeEmUsoException;
import com.joaopedroluz57.devfood.domain.exception.FormaPagamentoNaoEcontradaException;
import com.joaopedroluz57.devfood.domain.model.FormaPagamento;
import com.joaopedroluz57.devfood.domain.repository.FormaPagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FormaPagamentoService {

    public final String MSG_FORMA_DE_PAGAMENTO_EM_USO = "Forma de pagamento com o código %d não pode ser removida, pois está em uso";

    private final FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> buscarTodas() {
        return formaPagamentoRepository.findAll();
    }

    public FormaPagamento buscarOuFalharPorId(Long formaPagamentoId) {
        return formaPagamentoRepository.findById(formaPagamentoId)
                .orElseThrow(() -> new FormaPagamentoNaoEcontradaException(formaPagamentoId));
    }

    public OffsetDateTime buscarDataUltimaAtualizacao() {
        return formaPagamentoRepository.getDataUltimaAtualizacao();
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void deletar(Long formaPagamentoId) {
        try {
            formaPagamentoRepository.deleteById(formaPagamentoId);
            formaPagamentoRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEcontradaException(formaPagamentoId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_FORMA_DE_PAGAMENTO_EM_USO, formaPagamentoId));
        }
    }

}
