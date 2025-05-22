package br.com.fiap.checkpoint3.service;

import br.com.fiap.checkpoint3.dto.Paciente.PacienteRequestCreate;
import br.com.fiap.checkpoint3.dto.Paciente.PacienteRequestUpdate;
import br.com.fiap.checkpoint3.model.Paciente;
import br.com.fiap.checkpoint3.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    // Método para criar um novo paciente
    public Paciente createPaciente(PacienteRequestCreate dto) {
        Paciente paciente = new Paciente();
        paciente.setNome(dto.getNome());
        paciente.setEndereco(dto.getEndereco());
        paciente.setBairro(dto.getBairro());
        paciente.setEmail(dto.getEmail());
        paciente.setTelefone_completo(dto.getTelefoneCompleto());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setCreatedAt(LocalDateTime.now());
        paciente.setUpdatedAt(LocalDateTime.now());
        
        return pacienteRepository.save(paciente);
    }

    // Método para listar todos os pacientes com ordenação
    public List<Paciente> getAllPacientes(String sort) {
        Sort.Direction direction = sort.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        return pacienteRepository.findAll(Sort.by(direction, "nome"));
    }

    // Método para buscar um paciente por ID
    public Paciente getPacienteById(Long id) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        
        if (pacienteOptional.isEmpty()) {
            throw new RuntimeException("Paciente não encontrado");
        }
        
        return pacienteOptional.get();
    }

    // Método para atualizar um paciente existente
    public Paciente updatePaciente(Long id, PacienteRequestUpdate dto) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        
        if (pacienteOptional.isEmpty()) {
            throw new RuntimeException("Paciente não encontrado");
        }
        
        Paciente paciente = pacienteOptional.get();
        
        // Atualizar campos se fornecidos
        if (dto.getNome() != null) {
            paciente.setNome(dto.getNome());
        }
        
        if (dto.getEndereco() != null) {
            paciente.setEndereco(dto.getEndereco());
        }
        
        if (dto.getBairro() != null) {
            paciente.setBairro(dto.getBairro());
        }
        
        if (dto.getEmail() != null) {
            paciente.setEmail(dto.getEmail());
        }
        
        if (dto.getTelefoneCompleto() != null) {
            paciente.setTelefone_completo(dto.getTelefoneCompleto());
        }
        
        if (dto.getDataNascimento() != null) {
            paciente.setDataNascimento(dto.getDataNascimento());
        }
        
        paciente.setUpdatedAt(LocalDateTime.now());
        
        return pacienteRepository.save(paciente);
    }

    // Método para excluir um paciente
    public boolean deletePaciente(Long id) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        
        if (pacienteOptional.isEmpty()) {
            return false;
        }
        
        pacienteRepository.deleteById(id);
        return true;
    }
}
