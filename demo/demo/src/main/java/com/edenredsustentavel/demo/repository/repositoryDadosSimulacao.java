package com.edenredsustentavel.demo.repository;

import com.edenredsustentavel.demo.model.modelDadosSimulacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface repositoryDadosSimulacao extends JpaRepository<modelDadosSimulacao, Long> {
    List<modelDadosSimulacao> findByEmpresaIdOrderByDataCriacaoDesc(Long empresaId);
}