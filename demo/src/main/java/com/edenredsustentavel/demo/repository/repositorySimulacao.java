// repositorySimulacao.java
package com.edenredsustentavel.demo.repository;

import com.edenredsustentavel.demo.model.modelSimulacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface repositorySimulacao extends JpaRepository<modelSimulacao, Long> {

    // Buscar todas as simulações de uma empresa
    List<modelSimulacao> findByEmpresaId(Long empresaId);

    // Buscar simulações mais recentes primeiro
    List<modelSimulacao> findByEmpresaIdOrderByDataCriacaoDesc(Long empresaId);
}
