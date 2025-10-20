package com.example.APIShinyPET.Models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "agendamentos")
public class agendamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "usuario_id", nullable = false)
    private int usuarioId;

    @Column(name = "pet_id", nullable = false)
    private int petId;

    @Column(name = "data", nullable = false, columnDefinition = "DATE")
    private Date data;

    @Column(name = "hora", nullable = false)
    private Time hora;

    @Column(name = "servico", length = 100, nullable = false)
    private String servico;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('pendente', 'confirmado', 'concluido', 'cancelado')")
    private Status status;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private Timestamp criadoEm;

    @Column(name = "precoFinal", precision = 10, scale = 2, nullable = false)
    private BigDecimal precoFinal;


    public enum Status {
        pendente,
        confirmado,
        concluido,
        cancelado
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Timestamp criadoEm) {
        this.criadoEm = criadoEm;
    }

    public BigDecimal getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(BigDecimal precoFinal) {
        this.precoFinal = precoFinal;
    }
}
