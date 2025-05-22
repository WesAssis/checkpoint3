package br.com.fiap.checkpoint3.controller;

import br.com.fiap.checkpoint3.dto.Consulta.ConsultaResponse;
import br.com.fiap.checkpoint3.dto.Profissional.ProfissionalRequestCreate;
import br.com.fiap.checkpoint3.dto.Profissional.ProfissionalRequestUpdate;
import br.com.fiap.checkpoint3.dto.Profissional.ProfissionalResponse;
import br.com.fiap.checkpoint3.model.Consulta;
import br.com.fiap.checkpoint3.model.Profissional;
import br.com.fiap.checkpoint3.model.StatusConsulta;
import br.com.fiap.checkpoint3.service.ConsultaService;
import br.com.fiap.checkpoint3.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profissionais" )
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;
    
    @Autowired
    private ConsultaService consultaService;

    // Endpoint para criar um novo profissional
    @PostMapping
    public ResponseEntity<ProfissionalResponse> createProfissional(@RequestBody ProfissionalRequestCreate dto) {
        Profissional profissionalSalvo = profissionalService.createProfissional(dto);
        ProfissionalResponse response = mapToProfissionalResponse(profissionalSalvo);
        return ResponseEntity.status(201).body(response);
    }

    // Endpoint para listar profissionais com ordenação
    @GetMapping
    public ResponseEntity<List<ProfissionalResponse>> getAllProfissionais(
            @RequestParam(required = false, defaultValue = "asc") String sort) {
        
        List<Profissional> profissionais = profissionalService.getAllProfissionais(sort);
        
        List<ProfissionalResponse> response = profissionais.stream()
                .map(this::mapToProfissionalResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    // Endpoint para obter um profissional específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalResponse> getProfissionalById(@PathVariable Long id) {
        try {
            Profissional profissional = profissionalService.getProfissionalById(id);
            ProfissionalResponse response = mapToProfissionalResponse(profissional);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint para atualizar um profissional existente
    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalResponse> updateProfissional(@RequestBody ProfissionalRequestUpdate dto, @PathVariable Long id) {
        try {
            Profissional profissionalAtualizado = profissionalService.updateProfissional(id, dto);
            ProfissionalResponse response = mapToProfissionalResponse(profissionalAtualizado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint para excluir um profissional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfissional(@PathVariable Long id) {
        if (profissionalService.deleteProfissional(id))
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }
    
    // Endpoint para estatísticas do profissional
    @GetMapping("/{id}/stats")
    public ResponseEntity<Map<String, Object>> getProfissionalStats(@PathVariable Long id) {
        try {
            Map<String, Object> stats = consultaService.getProfissionalStats(id);
            return ResponseEntity.ok(stats);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint para consultas de um profissional com filtros
    @GetMapping("/{id}/consultas")
    public ResponseEntity<List<ConsultaResponse>> getConsultasByProfissional(
            @PathVariable Long id,
            @RequestParam(required = false) StatusConsulta status,
            @RequestParam(name = "data_de", required = false) 
                @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataInicio,
            @RequestParam(name = "data_ate", required = false) 
                @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataFim) {
        
        try {
            List<Consulta> consultas = consultaService.getConsultasByProfissional(id, status, dataInicio, dataFim);
            
            List<ConsultaResponse> response = consultas.stream()
                    .map(this::mapToConsultaResponse)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Método auxiliar para mapear Profissional para ProfissionalResponse
    private ProfissionalResponse mapToProfissionalResponse(Profissional profissional) {
        ProfissionalResponse response = new ProfissionalResponse();
        response.setId(profissional.getId());
        response.setNome(profissional.getNome());
        response.setEspecialidade(profissional.getEspecialidade());
        response.setValorHora(profissional.getValorHora());
        response.setCreatedAt(profissional.getCreatedAt());
        response.setUpdatedAt(profissional.getUpdatedAt());
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
