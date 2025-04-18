package com.joaopedroluz57.devfood.api.openapi.controller;

import com.joaopedroluz57.devfood.api.exceptionhandler.Problem;
import com.joaopedroluz57.devfood.api.model.PedidoModel;
import com.joaopedroluz57.devfood.api.model.PedidoResumidoModel;
import com.joaopedroluz57.devfood.api.model.input.PedidoInput;
import com.joaopedroluz57.devfood.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiOperation("Lista pedidos com paginação")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    Page<PedidoResumidoModel> buscarPaginada(PedidoFilter filtro, Pageable pageable);

    @ApiOperation("Busca pedido por código")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Código do pedido inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    PedidoModel buscarPorCodigo(@ApiParam(value = "Código de um pedido", example = "1") UUID codigoPedido);

    @ApiOperation("Adiciona novo pedido")
    @ApiResponses({@ApiResponse(code = 201, message = "Pedido adicionado")})
    PedidoModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma novo pedido")
                          PedidoInput pedidoInput);

}
