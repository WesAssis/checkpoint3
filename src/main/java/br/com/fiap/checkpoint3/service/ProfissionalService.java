package br.com.fiap.checkpoint3.service;

import br.com.fiap.checkpoint3.dto.Profissional.ProfissionalRequestCreate;
import br.com.fiap.checkpoint3.dto.Profissional.ProfissionalRequestUpdate;
import br.com.fiap.checkpoint3.model.Profissional;
import br.com.fiap.checkpoint3.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    // Método para criar um novo profissional
    public Profissional createProfissional(ProfissionalRequestCreate dto) {
        Profissional profissional = new Profissional();
        profissional.setNome(dto.getNome());
        profissional.setEspecialidade(dto.getEspecialidade());
        profissional.setValorHora(dto.getValorHora());
        profissional.setCreatedAt(LocalDateTime.now());
        profissional.setUpdatedAt(LocalDateTime.now());
        
        return profissionalRepository.save(profissional);
    }

    // Método para listar todos os profissionais com ordenação
    public List<Profissional> getAllProfissionais(String sort) {
        Sort.Direction direction = sort.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        return profissionalRepository.findAll(Sort.by(direction, "nome"));
    }

    // Método para buscar um profissional por ID
    public Profissional getProfissionalById(Long id) {
        Optional<Profissional> profissionalOptional = profissionalRepository.findById(id);
        
        if (profissionalOptional.isEmpty()) {
            throw new RuntimeException("Profissional não encontrado");
        }
        
        return profissionalOptional.get();
    }

    // Método para atualizar um profissional existente
    public Profissional updateProfissional(Long id, ProfissionalRequestUpdate dto) {
        Optional<Profissional> profissionalOptional = profissionalRepository.findById(id);
        
        if (profissionalOptional.isEmpty()) {
            throw new RuntimeException("Profissional não encontrado");
        }
        
        Profissional profissional = profissionalOptional.get();
        
        // Atualizar campos se fornecidos
        if (dto.getNome() != null) {
            profissional.setNome(dto.getNome());
        }
        
        if (dto.getEspecialidade() != null) {
            profissional.setEspecialidade(dto.getEspecialidade());
        }
        
        if (dto.getValorHora() != null) {
            profissional.setValorHora(dto.getValorHora());
        }
        
        profissional.setUpdatedAt(LocalDateTime.now());
        
        return profissionalRepository.save(profissional);
    }

    // Método para excluir um profissional
    public boolean deleteProfissional(Long id) {
        Optional<Profissional> profissionalOptional = profissionalRepository.findById(id);
        
        if (profissionalOptional.isEmpty()) {
            return false;
        }
        
        profissionalRepository.deleteById(id);
        return true;
    }
}
