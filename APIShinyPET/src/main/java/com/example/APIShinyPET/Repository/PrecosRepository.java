package com.example.APIShinyPET.Repository;

import com.example.APIShinyPET.Models.Precos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrecosRepository extends JpaRepository<Precos, Integer> {
    List<Precos> findByTamanho(String tamanho);
    List<Precos> findByServico(String servico);


}
