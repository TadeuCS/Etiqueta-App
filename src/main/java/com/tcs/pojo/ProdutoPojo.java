/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcs.pojo;

import java.math.BigDecimal;

/**
 *
 * @author Tadeu-PC
 */
public class ProdutoPojo {

    private Integer codigo;
    private String codBarras;
    private String descricao;
    private BigDecimal preco;
    private Integer qtde;
    private String validade;

    public ProdutoPojo() {

    }

    public ProdutoPojo(Integer codigo, String descricao, BigDecimal preco, String validade) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
        this.validade = validade;
    }

    public ProdutoPojo(Integer codigo, String descricao, BigDecimal preco, Integer qtde) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
        this.qtde = qtde;
    }

    public ProdutoPojo(Integer codigo, String descricao, BigDecimal preco, Integer qtde, String validade) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
        this.qtde = qtde;
        this.validade = validade;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQtde() {
        return qtde;
    }

    public void setQtde(Integer qtde) {
        this.qtde = qtde;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    @Override
    public String toString() {
        return "ProdutoPojo{" + "codigo=" + codigo + ", codBarras=" + codBarras + ", descricao=" + descricao + ", preco=" + preco + ", qtde=" + qtde + ", validade=" + validade + '}';
    }

}
