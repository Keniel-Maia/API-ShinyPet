package com.example.APIShinyPET.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "Animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Animal")
    private int idAnimal;

    @Column(name = "usuarioID", nullable = false)
    private int usuarioId;

    @Column(name = "Nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "Tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "Raca", nullable = false, length = 50)
    private String raca;

    @Column(name = "Idade", nullable = false, length = 20)
    private String idade;

    @Column(name = "Porte", nullable = false, length = 20)
    private String porte;

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }
}


