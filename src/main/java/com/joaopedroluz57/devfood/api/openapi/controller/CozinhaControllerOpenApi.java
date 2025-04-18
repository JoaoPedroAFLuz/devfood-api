package com.joaopedroluz57.devfood.api.openapi.controller;

import com.joaopedroluz57.devfood.api.exceptionhandler.Problem;
import com.joaopedroluz57.devfood.api.model.CozinhaModel;
import com.joaopedroluz57.devfood.api.model.input.CozinhaInput;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista cozinhas com paginação")
    Page<CozinhaModel> buscarPaginada(Pageable pageable);

    @ApiOperation("Busca cozinha por id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaModel buscarPorId(@ApiParam(value = "Id de uma cozinha", example = "1") Long cozinhaId);

    @ApiOperation("Lista cozinhas por nome")
    List<CozinhaModel> buscarPorNome(@ApiParam(value = "Nome da cozinha", example = "1") String nome);

    @ApiOperation("Adiciona nova cozinha")
    @ApiResponses({@ApiResponse(code = 201, message = "Cozinha adicionada")})
    CozinhaModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha")
                          CozinhaInput cozinhaInput);

    @ApiOperation("Atualiza cozinha")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada")
    })
    CozinhaModel atualizar(@ApiParam(value = "Id de uma cozinha", example = "1") Long cozinhaId,
                          @ApiParam(name = "corpo", value = "Representação da cozinha com novos dados")
                          CozinhaInput cozinhaInput);

    @ApiOperation("Remove cozinha")
    @ApiResponses({@ApiResponse(code = 204, message = "Cozinha removida")})
    void remover(@ApiParam(value = "Id de uma cozinha", example = "1") Long cozinhaId);

}
