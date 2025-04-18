package com.joaopedroluz57.devfood.api.openapi.controller;

import com.joaopedroluz57.devfood.api.exceptionhandler.Problem;
import com.joaopedroluz57.devfood.api.model.CidadeModel;
import com.joaopedroluz57.devfood.api.model.input.CidadeInput;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista cidades")
    List<CidadeModel> buscarTodas();

    @ApiOperation("Busca cidade por id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeModel buscarPorId(@ApiParam(value = "Id de uma cidade", example = "1") Long cidadeId);

    @ApiOperation("Lista cidades por estado")
    List<CidadeModel> buscarPorEstado(@ApiParam(value = "Id de um estado", example = "1") Long estadoId);

    @ApiOperation("Adiciona nova cidade")
    @ApiResponses({@ApiResponse(code = 201, message = "Cidade adicionada")})
    CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
                          CidadeInput cidadeInput);

    @ApiOperation("Atualiza cidade")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada")
    })
    CidadeModel atualizar(@ApiParam(value = "Id de uma cidade", example = "1") Long cidadeId,
                          @ApiParam(name = "corpo", value = "Representação da cidade com novos dados")
                          CidadeInput cidadeInput);

    @ApiOperation("Remove cidade")
    @ApiResponses({@ApiResponse(code = 204, message = "Cidade removida")})
    void remover(@ApiParam(value = "Id de uma cidade", example = "1") Long cidadeId);

}
