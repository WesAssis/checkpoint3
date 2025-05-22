package br.com.fiap.checkpoint3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.checkpoint3.model.Paciente;

public interface PacienteRepository 
        extends JpaRepository <Paciente, Long>{

}