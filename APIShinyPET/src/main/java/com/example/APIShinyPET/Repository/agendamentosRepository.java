package com.example.APIShinyPET.Repository;

import com.example.APIShinyPET.Models.agendamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.sql.Date; // <-- Import para o SQL Date
import java.sql.Time; // <-- Import para o SQL Time
import java.util.List;

@Repository
public interface agendamentosRepository extends JpaRepository<agendamentos, Integer> {
    List<agendamentos> findByServico(String servico);
    List<agendamentos> findByUsuarioId(int usuarioId);
    List<agendamentos> findByStatus(agendamentos.Status status);
    List<agendamentos> findByPetId(int petId);
    List<agendamentos> findByStatusNotIn(List<agendamentos.Status> statuses);
    List<agendamentos> findByDataAndHora(Date data, Time hora);
    List<agendamentos> findByData(Date data);
}
