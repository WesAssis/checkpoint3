package br.com.fiap.checkpoint3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.checkpoint3.model.Profissional;

public interface ProfissionalRepository 
        extends JpaRepository <Profissional, Long> {

}