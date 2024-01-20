package com.joaopedroluz57.devfood.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaopedroluz57.devfood.api.model.CozinhaModel;
import com.joaopedroluz57.devfood.api.model.RestauranteModel;
import com.joaopedroluz57.devfood.domain.exception.CozinhaNaoEncontradaException;
import com.joaopedroluz57.devfood.domain.exception.EntidadeNaoEncontradaException;
import com.joaopedroluz57.devfood.domain.exception.NegocioException;
import com.joaopedroluz57.devfood.domain.exception.ValidacaoException;
import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import com.joaopedroluz57.devfood.domain.model.input.RestauranteInput;
import com.joaopedroluz57.devfood.domain.service.RestauranteService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private SmartValidator validator;

    @GetMapping()
    public List<RestauranteModel> buscarTodos() {
        return restauranteService.buscarTodos().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscarPorId(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalharPorId(restauranteId);

        return toModel(restaurante);
    }

    @GetMapping("/por-nome")
    public List<RestauranteModel> buscarPorNome(String nome, Long cozinhaId) {
        return restauranteService.buscarPorNome(nome, cozinhaId).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/por-taxa-entrega")
    public List<RestauranteModel> buscarPorTaxaEntrega(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteService.buscarPorTaxaEntrega(taxaInicial, taxaFinal).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/por-nome-e-taxa-entrega")
    public List<RestauranteModel> buscarPorNomeETaxaEntrega(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteService.buscarPorNomeETaxaEntrega(nome, taxaInicial, taxaFinal).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/por-nome-e-frete-gratis")
    public List<RestauranteModel> buscarPorNomeETaxaEntregaGratis(String nome) {
        return restauranteService.buscarPorNomeEFreteGratis(nome).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = fromModel(restauranteInput);

            Restaurante restaurantePersistido = restauranteService.salvar(restaurante);

            return toModel(restaurantePersistido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante) {
        Restaurante restauranteAtual = restauranteService.buscarOuFalharPorId(restauranteId);

        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

        try {
            Restaurante restaurantePersistido = restauranteService.salvar(restaurante);

            return toModel(restaurantePersistido);
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PatchMapping("/{restauranteId}")
    public RestauranteModel atualizarParcialmente(
            @PathVariable Long restauranteId, @RequestBody Map<String, Object> campos, HttpServletRequest request
    ) {
        Restaurante restauranteAtual = restauranteService.buscarOuFalharPorId(restauranteId);

        merge(campos, restauranteAtual, request);

        validate(restauranteAtual);

        return atualizar(restauranteId, restauranteAtual);
    }

    @DeleteMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long restauranteId) {
        restauranteService.excluir(restauranteId);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            final Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);

                assert field != null;
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    private void validate(Restaurante restaurante) {
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(restaurante, "restaurante");

        validator.validate(restaurante, errors);

        if (errors.hasErrors()) {
            throw new ValidacaoException(errors);
        }
    }

    private RestauranteModel toModel(Restaurante restaurante) {
        CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(restaurante.getCozinha().getId());
        cozinhaModel.setNome(restaurante.getCozinha().getNome());

        RestauranteModel restauranteModel = new RestauranteModel();
        restauranteModel.setId(restaurante.getId());
        restauranteModel.setNome(restaurante.getNome());
        restauranteModel.setTaxaEntrega(restaurante.getTaxaEntrega());
        restauranteModel.setCozinha(cozinhaModel);

        return restauranteModel;
    }

    private Restaurante fromModel(RestauranteInput restauranteInput) {
        Restaurante restaurante = new Restaurante();

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());

        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaEntrega(restauranteInput.getTaxaEntrega());
        restaurante.setCozinha(cozinha);

        return restaurante;
    }

}
