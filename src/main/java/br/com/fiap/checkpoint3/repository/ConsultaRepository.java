package br.com.fiap.checkpoint3.repository;

import br.com.fiap.checkpoint3.model.Consulta;
import br.com.fiap.checkpoint3.model.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    
    // Consultas por paciente
    List<Consulta> findByPacienteId(Long pacienteId);
    
    // Consultas por profissional
    List<Consulta> findByProfissionalId(Long profissionalId);
    
    // Consultas por paciente com filtros
    List<Consulta> findByPacienteIdAndStatusConsulta(Long pacienteId, StatusConsulta statusConsulta);
    
    List<Consulta> findByPacienteIdAndDataBetween(Long pacienteId, LocalDateTime dataInicio, LocalDateTime dataFim);
    
    List<Consulta> findByPacienteIdAndStatusConsultaAndDataBetween(
            Long pacienteId, 
            StatusConsulta statusConsulta, 
            LocalDateTime dataInicio, 
            LocalDateTime dataFim);
    
    // Consultas por profissional com filtros
    List<Consulta> findByProfissionalIdAndStatusConsulta(Long profissionalId, StatusConsulta statusConsulta);
    
    List<Consulta> findByProfissionalIdAndDataBetween(Long profissionalId, LocalDateTime dataInicio, LocalDateTime dataFim);
    
    List<Consulta> findByProfissionalIdAndStatusConsultaAndDataBetween(
            Long profissionalId, 
            StatusConsulta statusConsulta, 
            LocalDateTime dataInicio, 
            LocalDateTime dataFim);
    
    // Consultas gerais com filtros
    List<Consulta> findByStatusConsulta(StatusConsulta statusConsulta);
    
    List<Consulta> findByDataBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    List<Consulta> findByStatusConsultaAndDataBetween(
            StatusConsulta statusConsulta, 
            LocalDateTime dataInicio, 
            LocalDateTime dataFim);
    
    // Estatísticas do profissional
    @Query("SELECT COUNT(c) FROM Consulta c WHERE c.profissional.id = :profissionalId")
    Long countByProfissionalId(Long profissionalId);
    
    @Query("SELECT COUNT(c) FROM Consulta c WHERE c.profissional.id = :profissionalId AND c.statusConsulta = :status")
    Long countByProfissionalIdAndStatus(Long profissionalId, StatusConsulta status);
    
    @Query("SELECT SUM(c.valorConsulta) FROM Consulta c WHERE c.profissional.id = :profissionalId AND c.statusConsulta = 'REALIZADA'")
    Double sumValorConsultaByProfissionalIdAndStatusRealizada(Long profissionalId);
}
