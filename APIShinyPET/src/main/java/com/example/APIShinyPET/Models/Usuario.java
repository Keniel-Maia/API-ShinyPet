package com.example.APIShinyPET.Models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Usuario")
    private int idUsuario;

    @Column(name = "Nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "Email", nullable = false, length = 80)
    private String email;

    @Column(name = "Senha", nullable = false, length = 255)
    private String senha;

    @Column(name = "token", length = 255)
    private String token;

    @Column(name = "token_expira")
    private LocalDateTime tokenExpira;

    @Column(name = "cpf", length = 14)
    private String cpf;

    @Column(name = "Nascimento", columnDefinition = "DATE")
    private LocalDate nascimento;

    @Column(name = "logradouro", length = 255)
    private String logradouro;

    @Column(name = "numero", length = 20)
    private String numero;

    @Column(name = "bairro", length = 100)
    private String bairro;

    @Column(name = "cep", length = 10)
    private String cep;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @Column(name = "aceitouTermos", nullable = false)
    private boolean aceitouTermos;

    @Column(name = "dataCadastro", nullable = false)
    private Timestamp dataCadastro;

    @Column(name = "Telefone", length = 20)
    private String telefone;

    // 0=cliente, 1=tosador, 2=admin
    @Column(name = "nivel", nullable = false)
    private int nivel;


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getTokenExpira() {
        return tokenExpira;
    }

    public void setTokenExpira(LocalDateTime tokenExpira) {
        this.tokenExpira = tokenExpira;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public boolean isAceitouTermos() {
        return aceitouTermos;
    }

    public void setAceitouTermos(boolean aceitouTermos) {
        this.aceitouTermos = aceitouTermos;
    }

    public Timestamp getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = Timestamp.valueOf(dataCadastro);
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
