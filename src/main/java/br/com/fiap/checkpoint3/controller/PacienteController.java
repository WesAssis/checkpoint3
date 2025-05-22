package br.com.fiap.checkpoint3.controller;

import br.com.fiap.checkpoint3.dto.Consulta.ConsultaResponse;
import br.com.fiap.checkpoint3.dto.Paciente.PacienteRequestCreate;
import br.com.fiap.checkpoint3.dto.Paciente.PacienteRequestUpdate;
import br.com.fiap.checkpoint3.dto.Paciente.PacienteResponse;
import br.com.fiap.checkpoint3.model.Consulta;
import br.com.fiap.checkpoint3.model.Paciente;
import br.com.fiap.checkpoint3.model.StatusConsulta;
import br.com.fiap.checkpoint3.service.ConsultaService;
import br.com.fiap.checkpoint3.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pacientes" )
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;
    
    @Autowired
    private ConsultaService consultaService;

    // Endpoint para criar um novo paciente
    @PostMapping
    public ResponseEntity<PacienteResponse> createPaciente(@RequestBody PacienteRequestCreate dto) {
        Paciente pacienteSalvo = pacienteService.createPaciente(dto);
        PacienteResponse response = mapToPacienteResponse(pacienteSalvo);
        return ResponseEntity.status(201).body(response);
    }

    // Endpoint para listar pacientes com ordenação
    @GetMapping
    public ResponseEntity<List<PacienteResponse>> getAllPacientes(
            @RequestParam(required = false, defaultValue = "asc") String sort) {
        
        List<Paciente> pacientes = pacienteService.getAllPacientes(sort);
        
        List<PacienteResponse> response = pacientes.stream()
                .map(this::mapToPacienteResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    // Endpoint para obter um paciente específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> getPacienteById(@PathVariable Long id) {
        try {
            Paciente paciente = pacienteService.getPacienteById(id);
            PacienteResponse response = mapToPacienteResponse(paciente);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint para atualizar um paciente existente
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> updatePaciente(@RequestBody PacienteRequestUpdate dto, @PathVariable Long id) {
        try {
            Paciente pacienteAtualizado = pacienteService.updatePaciente(id, dto);
            PacienteResponse response = mapToPacienteResponse(pacienteAtualizado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint para excluir um paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        if (pacienteService.deletePaciente(id))
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }
    
    // Endpoint para consultas de um paciente com filtros
    @GetMapping("/{id}/consultas")
    public ResponseEntity<List<ConsultaResponse>> getConsultasByPaciente(
            @PathVariable Long id,
            @RequestParam(required = false) StatusConsulta status,
            @RequestParam(name = "data_de", required = false) 
                @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataInicio,
            @RequestParam(name = "data_ate", required = false) 
                @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataFim) {
        
        try {
            List<Consulta> consultas = consultaService.getConsultasByPaciente(id, status, dataInicio, dataFim);
            
            List<ConsultaResponse> response = consultas.stream()
                    .map(this::mapToConsultaResponse)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Método auxiliar para mapear Paciente para PacienteResponse
    private PacienteResponse mapToPacienteResponse(Paciente paciente) {
        PacienteResponse response = new PacienteResponse();
        response.setId(paciente.getId());
        response.setNome(paciente.getNome());
        response.setEndereco(paciente.getEndereco());
        response.setBairro(paciente.getBairro());
        response.setEmail(paciente.getEmail());
        response.setTelefoneCompleto(paciente.getTelefone_completo());
        response.setDataNascimento(paciente.getDataNascimento());
        response.setCreatedAt(paciente.getCreatedAt());
        response.setUpdatedAt(paciente.getUpdatedAt());
        return response;
    }
    
    // Método auxiliar para mapear Consulta para ConsultaResponse
    private ConsultaResponse mapToConsultaResponse(Consulta consulta) {
        ConsultaResponse response = new ConsultaResponse();
        response.setId(consulta.getId());
        response.setPacienteId(consulta.getPaciente().getId());
        response.setProfissionalId(consulta.getProfissional().getId());
        response.setDataConsulta(consulta.getData());
        response.setQuantidadeHoras(consulta.getQuantidadeHoras());
        response.setStatusConsulta(consulta.getStatusConsulta());
        response.setValorConsulta(consulta.getValorConsulta());
        response.setCreatedAt(consulta.getCreatedAt());
        response.setUpdatedAt(consulta.getUpdatedAt());
        return response;
    }
}
