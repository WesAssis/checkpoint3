package br.com.fiap.checkpoint3.dto.Consulta;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import br.com.fiap.checkpoint3.model.Consulta;
import br.com.fiap.checkpoint3.model.StatusConsulta;

public class ConsultaResponse {
    private Long id;
    private Long pacienteId;
    private Long profissionalId;
    private LocalDateTime dataConsulta;
    private StatusConsulta statusConsulta;
    private BigInteger quantidadeHoras;
    private BigDecimal valorConsulta;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Método estático para criar um ConsultaResponse a partir de uma Consulta
    public static ConsultaResponse from(Consulta consulta) {
        ConsultaResponse response = new ConsultaResponse();
        response.id = consulta.getId();
        response.pacienteId = consulta.getPaciente().getId();
        response.profissionalId = consulta.getProfissional().getId();
        response.dataConsulta = consulta.getData();
        response.statusConsulta = consulta.getStatusConsulta();
        response.quantidadeHoras = consulta.getQuantidadeHoras();
        response.valorConsulta = consulta.getValorConsulta();
        response.createdAt = consulta.getCreatedAt();
        response.updatedAt = consulta.getUpdatedAt();
        return response;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public StatusConsulta getStatusConsulta() {
        return statusConsulta;
    }

    public void setStatusConsulta(StatusConsulta statusConsulta) {
        this.statusConsulta = statusConsulta;
    }

    public BigInteger getQuantidadeHoras() {
        return quantidadeHoras;
    }

    public void setQuantidadeHoras(BigInteger quantidadeHoras) {
        this.quantidadeHoras = quantidadeHoras;
    }

    public BigDecimal getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(BigDecimal valorConsulta) {
        this.valorConsulta = valorConsulta;
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
