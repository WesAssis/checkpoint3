package br.com.fiap.checkpoint3.dto.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.fiap.checkpoint3.model.Paciente;

public class PacienteResponse {
    private Long id;
    private String nome;
    private String endereco;
    private String bairro;
    private String email;
    private String telefoneCompleto;
    private LocalDate dataNascimento;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Método estático para criar um PacienteResponse a partir de um Paciente
    public static PacienteResponse from(Paciente paciente) {
        PacienteResponse response = new PacienteResponse();
        response.id = paciente.getId();
        response.nome = paciente.getNome();
        response.endereco = paciente.getEndereco();
        response.bairro = paciente.getBairro();
        response.email = paciente.getEmail();
        response.telefoneCompleto = paciente.getTelefone_completo();
        response.dataNascimento = paciente.getDataNascimento();
        response.createdAt = paciente.getCreatedAt();
        response.updatedAt = paciente.getUpdatedAt();
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefoneCompleto() {
        return telefoneCompleto;
    }

    public void setTelefoneCompleto(String telefoneCompleto) {
        this.telefoneCompleto = telefoneCompleto;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
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
