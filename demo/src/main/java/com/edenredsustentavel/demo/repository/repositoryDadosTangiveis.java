package com.edenredsustentavel.demo.repository;

import com.edenredsustentavel.demo.model.modelDadosTangiveis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repositoryDadosTangiveis extends JpaRepository<modelDadosTangiveis, Long> {
    modelDadosTangiveis findBySimulacaoId(Long simulacaoId);
}
