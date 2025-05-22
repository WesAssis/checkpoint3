package br.com.fiap.checkpoint3.dto.Profissional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.fiap.checkpoint3.model.Profissional;

public class ProfissionalResponse {
    private Long id;
    private String nome;
    private String especialidade;
    private BigDecimal valorHora;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Método estático para criar um ProfissionalResponse a partir de um Profissional
    public static ProfissionalResponse from(Profissional profissional) {
        ProfissionalResponse response = new ProfissionalResponse();
        response.id = profissional.getId();
        response.nome = profissional.getNome();
        response.especialidade = profissional.getEspecialidade();
        response.valorHora = profissional.getValorHora();
        response.createdAt = profissional.getCreatedAt();
        response.updatedAt = profissional.getUpdatedAt();
        return response;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
