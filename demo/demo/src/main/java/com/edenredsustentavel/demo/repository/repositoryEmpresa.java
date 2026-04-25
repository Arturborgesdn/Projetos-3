// repositoryEmpresa.java
package com.edenredsustentavel.demo.repository;

import com.edenredsustentavel.demo.model.modelEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface repositoryEmpresa extends JpaRepository<modelEmpresa, Long> {

    Optional<modelEmpresa> findByCnpj(String cnpj);
    Optional<modelEmpresa> findByEmail(String email);
}
