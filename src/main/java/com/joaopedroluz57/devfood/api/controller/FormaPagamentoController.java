package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.api.assembler.FormaPagamentoInputDisassembler;
import com.joaopedroluz57.devfood.api.assembler.FormaPagamentoModelAssembler;
import com.joaopedroluz57.devfood.api.model.FormaPagamentoModel;
import com.joaopedroluz57.devfood.api.model.input.FormaPagamentoInput;
import com.joaopedroluz57.devfood.domain.model.FormaPagamento;
import com.joaopedroluz57.devfood.domain.service.FormaPagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("formas-pagamento")
@RestController
public class FormaPagamentoController {

    private final FormaPagamentoService formaPagamentoService;
    private final FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    private final FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    public FormaPagamentoController(FormaPagamentoService formaPagamentoService,
                                    FormaPagamentoModelAssembler formaPagamentoModelAssembler,
                                    FormaPagamentoInputDisassembler formaPagamentoInputDisassembler) {
        this.formaPagamentoService = formaPagamentoService;
        this.formaPagamentoModelAssembler = formaPagamentoModelAssembler;
        this.formaPagamentoInputDisassembler = formaPagamentoInputDisassembler;
    }

    @GetMapping
    public List<FormaPagamentoModel> buscarTodas() {
        return formaPagamentoService.buscarTodas().stream()
                .map(formaPagamentoModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{formasPagamentoId}")
    public FormaPagamentoModel buscarTodas(@PathVariable Long formasPagamentoId) {
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalharPorId(formasPagamentoId);

        return formaPagamentoModelAssembler.toModel(formaPagamento);
    }

    @PostMapping
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);

        FormaPagamento formaPagamentoPersistido = formaPagamentoService.salvar(formaPagamento);

        return formaPagamentoModelAssembler.toModel(formaPagamentoPersistido);
    }

    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoModel atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalharPorId(formaPagamentoId);

        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamento);

        FormaPagamento formaPagamentoPersistido = formaPagamentoService.salvar(formaPagamento);

        return formaPagamentoModelAssembler.toModel(formaPagamentoPersistido);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long formaPagamentoId) {
        formaPagamentoService.deletar(formaPagamentoId);
    }

}
