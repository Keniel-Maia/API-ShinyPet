package com.example.APIShinyPET.Repository;

import com.example.APIShinyPET.Models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    List<Animal> findByUsuarioId(int usuarioId); // correto!
    List<Animal> findByNome(String nome);
    List<Animal> findByTipo(String tipo);


}
