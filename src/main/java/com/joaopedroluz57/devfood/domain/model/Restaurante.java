package com.joaopedroluz57.devfood.domain.model;

import com.joaopedroluz57.devfood.core.validation.ValorZeroIncluiDescricao;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ValorZeroIncluiDescricao(valorField = "taxaEntrega", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
public class Restaurante {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal taxaEntrega;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cozinha cozinha;

    @Column(nullable = false)
    private boolean ativo = Boolean.TRUE;

    @Column(nullable = false)
    private boolean aberto = Boolean.FALSE;

    @Embedded
    private Endereco endereco;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos;

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> resposaveis = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    public void ativar() {
        setAtivo(true);
    }

    public void desativar() {
        setAtivo(false);
    }

    public void abrir() {
        setAberto(true);
    }

    public void fechar() {
        setAberto(false);
    }

    public void adicionarFormaPagamento(FormaPagamento formaPagamento) {
        this.getFormasPagamento().add(formaPagamento);
    }

    public void removerFormaPagamento(FormaPagamento formaPagamento) {
        this.getFormasPagamento().remove(formaPagamento);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamento().contains(formaPagamento);
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
        return !aceitaFormaPagamento(formaPagamento);
    }

    public void adicionarResponsavel(Usuario usuario) {
        this.getResposaveis().add(usuario);
    }

    public void removerResponsavel(Usuario usuario) {
        this.getResposaveis().remove(usuario);
    }

}
