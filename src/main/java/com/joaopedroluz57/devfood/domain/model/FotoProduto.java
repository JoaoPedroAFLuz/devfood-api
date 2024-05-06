package com.joaopedroluz57.devfood.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "foto_produto")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FotoProduto {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "produto_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Produto produto;

    private String nomeArquivo;

    private String descricao;

    private String tipoArquivo;

    private Long tamanho;

    public Long getRestauranteId() {
        if (!Objects.isNull(getProduto())) {
            return getProduto().getRestaurante().getId();
        }

        return null;
    }

}
