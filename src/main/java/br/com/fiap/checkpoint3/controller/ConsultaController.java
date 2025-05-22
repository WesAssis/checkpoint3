package br.com.fiap.checkpoint3.controller;

import br.com.fiap.checkpoint3.dto.Consulta.ConsultaRequestCreate;
import br.com.fiap.checkpoint3.dto.Consulta.ConsultaRequestUpdate;
import br.com.fiap.checkpoint3.dto.Consulta.ConsultaResponse;
import br.com.fiap.checkpoint3.model.Consulta;
import br.com.fiap.checkpoint3.model.StatusConsulta;
import br.com.fiap.checkpoint3.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/consultas" )
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    // Endpoint para criar uma nova consulta
    @PostMapping
    public ResponseEntity<ConsultaResponse> createConsulta(@RequestBody ConsultaRequestCreate dto) {
        Consulta consultaSalva = consultaService.createConsulta(dto);
        ConsultaResponse response = mapToConsultaResponse(consultaSalva);
        return ResponseEntity.status(201).body(response);
    }

    // Endpoint para listar consultas com filtros
    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> getConsultas(
            @RequestParam(required = false) StatusConsulta status,
            @RequestParam(name = "data_de", required = false) 
                @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataInicio,
            @RequestParam(name = "data_ate", required = false) 
                @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataFim) {
        
        List<Consulta> consultas = consultaService.getConsultasWithFilters(status, dataInicio, dataFim);
        
        List<ConsultaResponse> response = consultas.stream()
                .map(this::mapToConsultaResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    // Endpoint para obter uma consulta específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponse> getConsultaById(@PathVariable Long id) {
        try {
            Consulta consulta = consultaService.getConsultaById(id);
            ConsultaResponse response = mapToConsultaResponse(consulta);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint para atualizar uma consulta existente
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponse> updateConsulta(@RequestBody ConsultaRequestUpdate dto, @PathVariable Long id) {
        try {
            Consulta consultaAtualizada = consultaService.updateConsulta(id, dto);
            ConsultaResponse response = mapToConsultaResponse(consultaAtualizada);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint para excluir uma consulta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsulta(@PathVariable Long id) {
        if (consultaService.deleteConsulta(id))
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
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
