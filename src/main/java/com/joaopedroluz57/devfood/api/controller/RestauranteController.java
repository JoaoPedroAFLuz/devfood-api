package com.joaopedroluz57.devfood.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaopedroluz57.devfood.api.assembler.RestauranteInputAssembler;
import com.joaopedroluz57.devfood.api.assembler.RestauranteInputDisassembler;
import com.joaopedroluz57.devfood.api.assembler.RestauranteModelAssembler;
import com.joaopedroluz57.devfood.api.model.RestauranteModel;
import com.joaopedroluz57.devfood.api.model.input.RestauranteInput;
import com.joaopedroluz57.devfood.api.openapi.model.RestauranteBasicoModelOpenApi;
import com.joaopedroluz57.devfood.api.view.RestauranteView;
import com.joaopedroluz57.devfood.domain.exception.EntidadeNaoEncontradaException;
import com.joaopedroluz57.devfood.domain.exception.NegocioException;
import com.joaopedroluz57.devfood.domain.exception.RestauranteNaoEncontradoException;
import com.joaopedroluz57.devfood.domain.exception.ValidacaoException;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import com.joaopedroluz57.devfood.domain.service.RestauranteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@Api(tags = "Restaurantes")
@RequiredArgsConstructor
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {

    private final SmartValidator validator;
    private final RestauranteService restauranteService;
    private final RestauranteModelAssembler restauranteModelAssembler;
    private final RestauranteInputAssembler restauranteInputAssembler;
    private final RestauranteInputDisassembler restauranteInputDisassembler;

    @GetMapping()
    @JsonView(RestauranteView.Resumo.class)
    @ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projecao", value = "Nome da projeção de restaurantes", allowableValues = "apenas-nome",
                    dataType = "string", paramType = "query")
    })
    public List<RestauranteModel> buscarTodos() {
        return restauranteService.buscarTodos().stream()
                .map(restauranteModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping(params = "projecao=apenas-nome")
    @JsonView(RestauranteView.ApenasNome.class)
    @ApiOperation(value = "Lista restaurantes", hidden = true)
    public List<RestauranteModel> buscarTodosNomes() {
        return buscarTodos();
    }

//    @GetMapping()
//    public MappingJacksonValue buscarTodos(@RequestParam(required = false) String projecao) {
//        List<RestauranteModel> restaurantesModel = restauranteService.buscarTodos().stream()
//                .map(restauranteModelAssembler::toModel)
//                .collect(Collectors.toList());
//
//        MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesModel);
//        restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//
//        if ("completo".equals(projecao)) {
//            restaurantesWrapper.setSerializationView(null);
//        } else if ("apenas-nome".equals(projecao)) {
//            restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//        }
//
//        return restaurantesWrapper;
//    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscarPorId(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalharPorId(restauranteId);

        return restauranteModelAssembler.toModel(restaurante);
    }

    @GetMapping("/por-nome")
    public List<RestauranteModel> buscarPorNome(String nome, Long cozinhaId) {
        return restauranteService.buscarPorNome(nome, cozinhaId).stream()
                .map(restauranteModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/por-taxa-entrega")
    public List<RestauranteModel> buscarPorTaxaEntrega(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteService.buscarPorTaxaEntrega(taxaInicial, taxaFinal).stream()
                .map(restauranteModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/por-nome-e-taxa-entrega")
    public List<RestauranteModel> buscarPorNomeETaxaEntrega(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteService.buscarPorNomeETaxaEntrega(nome, taxaInicial, taxaFinal).stream()
                .map(restauranteModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/por-nome-e-frete-gratis")
    public List<RestauranteModel> buscarPorNomeETaxaEntregaGratis(String nome) {
        return restauranteService.buscarPorNomeEFreteGratis(nome).stream()
                .map(restauranteModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

            Restaurante restaurantePersistido = restauranteService.salvar(restaurante);

            return restauranteModelAssembler.toModel(restaurantePersistido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable Long restauranteId,
                                      @RequestBody @Valid RestauranteInput restauranteInput) {
        Restaurante restauranteAtual = restauranteService.buscarOuFalharPorId(restauranteId);

        restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

        try {
            Restaurante restaurantePersistido = restauranteService.salvar(restauranteAtual);

            return restauranteModelAssembler.toModel(restaurantePersistido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abertura(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechamento(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
    }

    @PatchMapping("/{restauranteId}")
    public RestauranteModel atualizarParcialmente(
            @PathVariable Long restauranteId, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Restaurante restauranteAtual = restauranteService.buscarOuFalharPorId(restauranteId);

        merge(campos, restauranteAtual, request);
        validate(restauranteAtual);

        RestauranteInput restauranteInput = restauranteInputAssembler.toInput(restauranteAtual);

        return atualizar(restauranteId, restauranteInput);
    }

    @DeleteMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long restauranteId) {
        restauranteService.excluir(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/desativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativar(@PathVariable Long restauranteId) {
        restauranteService.desativar(restauranteId);
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.desativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
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

}
