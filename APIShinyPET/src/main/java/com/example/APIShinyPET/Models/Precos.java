package com.example.APIShinyPET.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "Precos")
public class Precos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tamanho", nullable = false, length = 50)
    private String tamanho;

    @Column(name = "servico", nullable = false, length = 100)
    private String servico;

    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal preco;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}


