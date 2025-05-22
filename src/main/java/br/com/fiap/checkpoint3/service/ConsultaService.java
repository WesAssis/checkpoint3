package br.com.fiap.checkpoint3.service;

import br.com.fiap.checkpoint3.dto.Consulta.ConsultaRequestCreate;
import br.com.fiap.checkpoint3.dto.Consulta.ConsultaRequestUpdate;
import br.com.fiap.checkpoint3.model.Consulta;
import br.com.fiap.checkpoint3.model.Paciente;
import br.com.fiap.checkpoint3.model.Profissional;
import br.com.fiap.checkpoint3.model.StatusConsulta;
import br.com.fiap.checkpoint3.repository.ConsultaRepository;
import br.com.fiap.checkpoint3.repository.PacienteRepository;
import br.com.fiap.checkpoint3.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    // Método para criar uma nova consulta
    public Consulta createConsulta(ConsultaRequestCreate dto) {
        // Buscar paciente e profissional
        Optional<Paciente> paciente = pacienteRepository.findById(dto.getPacienteId());
        Optional<Profissional> profissional = profissionalRepository.findById(dto.getProfissionalId());

        if (paciente.isEmpty()) {
            throw new RuntimeException("Paciente não encontrado");
        }

        if (profissional.isEmpty()) {
            throw new RuntimeException("Profissional não encontrado");
        }

        // Calcular valor da consulta
        BigDecimal valorHora = profissional.get().getValorHora();
        BigDecimal valorConsulta = valorHora.multiply(new BigDecimal(dto.getQuantidadeHoras()));

        // Criar nova consulta
        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente.get());
        consulta.setProfissional(profissional.get());
        consulta.setData(dto.getDataConsulta());
        consulta.setStatusConsulta(dto.getStatusConsulta());
        consulta.setQuantidadeHoras(dto.getQuantidadeHoras());
        consulta.setValorConsulta(valorConsulta);
        consulta.setCreatedAt(LocalDateTime.now());
        consulta.setUpdatedAt(LocalDateTime.now());

        return consultaRepository.save(consulta);
    }

    // Método para atualizar uma consulta existente
    public Consulta updateConsulta(Long id, ConsultaRequestUpdate dto) {
        Optional<Consulta> consultaOptional = consultaRepository.findById(id);

        if (consultaOptional.isEmpty()) {
            throw new RuntimeException("Consulta não encontrada");
        }

        Consulta consulta = consultaOptional.get();

        // Atualizar paciente se fornecido
        if (dto.getPacienteId() != null) {
            Optional<Paciente> paciente = pacienteRepository.findById(dto.getPacienteId());
            if (paciente.isEmpty()) {
                throw new RuntimeException("Paciente não encontrado");
            }
            consulta.setPaciente(paciente.get());
        }

        // Atualizar profissional se fornecido
        if (dto.getProfissionalId() != null) {
            Optional<Profissional> profissional = profissionalRepository.findById(dto.getProfissionalId());
            if (profissional.isEmpty()) {
                throw new RuntimeException("Profissional não encontrado");
            }
            consulta.setProfissional(profissional.get());
        }

        // Atualizar outros campos se fornecidos
        if (dto.getDataConsulta() != null) {
            consulta.setData(dto.getDataConsulta());
        }

        if (dto.getStatusConsulta() != null) {
            consulta.setStatusConsulta(dto.getStatusConsulta());
        }

        if (dto.getQuantidadeHoras() != null) {
            consulta.setQuantidadeHoras(dto.getQuantidadeHoras());
            
            // Recalcular valor da consulta
            BigDecimal valorHora = consulta.getProfissional().getValorHora();
            BigDecimal valorConsulta = valorHora.multiply(new BigDecimal(dto.getQuantidadeHoras()));
            consulta.setValorConsulta(valorConsulta);
    }

        if (dto.getValorConsulta() != null) {
            consulta.setValorConsulta(dto.getValorConsulta());
        }

        consulta.setUpdatedAt(LocalDateTime.now());

        return consultaRepository.save(consulta);
    }

    // Método para excluir uma consulta
    public boolean deleteConsulta(Long id) {
        Optional<Consulta> consultaOptional = consultaRepository.findById(id);

        if (consultaOptional.isEmpty()) {
            return false;
        }

        consultaRepository.deleteById(id);
        return true;
    }

    // Método para buscar uma consulta por ID
    public Consulta getConsultaById(Long id) {
        Optional<Consulta> consultaOptional = consultaRepository.findById(id);

        if (consultaOptional.isEmpty()) {
            throw new RuntimeException("Consulta não encontrada");
        }

        return consultaOptional.get();
    }

    // Método para listar todas as consultas
    public List<Consulta> getAll() {
        return consultaRepository.findAll();
    }

    // Método para buscar consultas de um paciente com filtros
    public List<Consulta> getConsultasByPaciente(Long pacienteId, StatusConsulta status, 
                                                LocalDate dataInicio, LocalDate dataFim) {
        // Verificar se o paciente existe
        Optional<Paciente> paciente = pacienteRepository.findById(pacienteId);
        if (paciente.isEmpty()) {
            throw new RuntimeException("Paciente não encontrado");
        }
        
        // Converter LocalDate para LocalDateTime (início e fim do dia)
        LocalDateTime dataInicioDateTime = dataInicio != null ? 
                dataInicio.atStartOfDay() : null;
        LocalDateTime dataFimDateTime = dataFim != null ? 
                dataFim.atTime(LocalTime.MAX) : null;
        
        // Aplicar filtros conforme parâmetros fornecidos
        if (status != null && dataInicioDateTime != null && dataFimDateTime != null) {
            return consultaRepository.findByPacienteIdAndStatusConsultaAndDataBetween(
                    pacienteId, status, dataInicioDateTime, dataFimDateTime);
        } else if (status != null) {
            return consultaRepository.findByPacienteIdAndStatusConsulta(pacienteId, status);
        } else if (dataInicioDateTime != null && dataFimDateTime != null) {
            return consultaRepository.findByPacienteIdAndDataBetween(
                    pacienteId, dataInicioDateTime, dataFimDateTime);
        } else {
            // Buscar todas as consultas do paciente se nenhum filtro for fornecido
            return consultaRepository.findByPacienteId(pacienteId);
        }
    }
    
    // Método para buscar consultas de um profissional com filtros
    public List<Consulta> getConsultasByProfissional(Long profissionalId, StatusConsulta status, 
                                                    LocalDate dataInicio, LocalDate dataFim) {
        // Verificar se o profissional existe
        Optional<Profissional> profissional = profissionalRepository.findById(profissionalId);
        if (profissional.isEmpty()) {
            throw new RuntimeException("Profissional não encontrado");
        }
        
        // Converter LocalDate para LocalDateTime (início e fim do dia)
        LocalDateTime dataInicioDateTime = dataInicio != null ? 
                dataInicio.atStartOfDay() : null;
        LocalDateTime dataFimDateTime = dataFim != null ? 
                dataFim.atTime(LocalTime.MAX) : null;
        
        // Aplicar filtros conforme parâmetros fornecidos
        if (status != null && dataInicioDateTime != null && dataFimDateTime != null) {
            return consultaRepository.findByProfissionalIdAndStatusConsultaAndDataBetween(
                    profissionalId, status, dataInicioDateTime, dataFimDateTime);
        } else if (status != null) {
            return consultaRepository.findByProfissionalIdAndStatusConsulta(profissionalId, status);
        } else if (dataInicioDateTime != null && dataFimDateTime != null) {
            return consultaRepository.findByProfissionalIdAndDataBetween(
                    profissionalId, dataInicioDateTime, dataFimDateTime);
        } else {
            // Buscar todas as consultas do profissional se nenhum filtro for fornecido
            return consultaRepository.findByProfissionalId(profissionalId);
        }
    }
    
    // Método para buscar consultas com filtros
    public List<Consulta> getConsultasWithFilters(StatusConsulta status, 
                                                LocalDate dataInicio, LocalDate dataFim) {
        // Converter LocalDate para LocalDateTime (início e fim do dia)
        LocalDateTime dataInicioDateTime = dataInicio != null ? 
                dataInicio.atStartOfDay() : null;
        LocalDateTime dataFimDateTime = dataFim != null ? 
                dataFim.atTime(LocalTime.MAX) : null;
        
        // Aplicar filtros conforme parâmetros fornecidos
        if (status != null && dataInicioDateTime != null && dataFimDateTime != null) {
            return consultaRepository.findByStatusConsultaAndDataBetween(
                    status, dataInicioDateTime, dataFimDateTime);
        } else if (status != null) {
            return consultaRepository.findByStatusConsulta(status);
        } else if (dataInicioDateTime != null && dataFimDateTime != null) {
            return consultaRepository.findByDataBetween(dataInicioDateTime, dataFimDateTime);
        } else {
            // Buscar todas as consultas se nenhum filtro for fornecido
            return consultaRepository.findAll();
        }
    }
    
    // Método para obter estatísticas de um profissional
    public Map<String, Object> getProfissionalStats(Long profissionalId) {
        // Verificar se o profissional existe
        Optional<Profissional> profissional = profissionalRepository.findById(profissionalId);
        if (profissional.isEmpty()) {
            throw new RuntimeException("Profissional não encontrado");
        }
        
        // Obter estatísticas
        Long totalConsultas = consultaRepository.countByProfissionalId(profissionalId);
        Long consultasAgendadas = consultaRepository.countByProfissionalIdAndStatus(
                profissionalId, StatusConsulta.AGENDADA);
        Long consultasRealizadas = consultaRepository.countByProfissionalIdAndStatus(
                profissionalId, StatusConsulta.REALIZADA);
        Long consultasCanceladas = consultaRepository.countByProfissionalIdAndStatus(
                profissionalId, StatusConsulta.CANCELADA);
        Double valorTotal = consultaRepository.sumValorConsultaByProfissionalIdAndStatusRealizada(
                profissionalId);
        
        // Criar mapa com as estatísticas
        return Map.of(
                "profissional", profissional.get(),
                "totalConsultas", totalConsultas,
                "consultasAgendadas", consultasAgendadas,
                "consultasRealizadas", consultasRealizadas,
                "consultasCanceladas", consultasCanceladas,
                "valorTotal", valorTotal != null ? valorTotal : 0.0
        );
    }
}
