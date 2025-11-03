package com.example.APIShinyPET.Controller;

import com.example.APIShinyPET.Models.agendamentos;
import com.example.APIShinyPET.Repository.agendamentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// EXTRAS PARA CONVERSÃO DE DATA
import java.sql.Date;
import java.sql.Time;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/apiAgendamentos")
public class agendamentosController {
    @Autowired
    private agendamentosRepository agendamentoRepo;

    // Buscar todos os agendamentos
    @GetMapping("/listarAgendamentos")
    public List<agendamentos> listarTodosAgendamentos() {
        return agendamentoRepo.findAll();
    }

    // INSERIR AGENDAMENTO COM VERIFICAÇÃO DE CONFLITO!
    @PostMapping("/inserirAgendamento")
    public ResponseEntity<?> inserirAgendamento(@RequestBody agendamentos agendamento) {
        if (agendamento.getData() == null || agendamento.getHora() == null || agendamento.getServico() == null ||
                agendamento.getUsuarioId() == 0 || agendamento.getPetId() == 0) {
            return ResponseEntity.badRequest().body("Todos os campos são obrigatórios.");
        }

        // CHECANDO CONFLITO DE DIA + HORA (APENAS SE NÃO CANCELADO/CONCLUIDO)
        List<agendamentos> existentes = agendamentoRepo.findByDataAndHora(
                new Date(agendamento.getData().getTime()),
                agendamento.getHora() // já é java.sql.Time
        );

        boolean conflito = existentes.stream().anyMatch(ag ->
                ag.getStatus() != null &&
                        ag.getStatus() != agendamentos.Status.cancelado &&
                        ag.getStatus() != agendamentos.Status.concluido
        );
        if (conflito) {
            return ResponseEntity.status(409).body("Já existe agendamento para este dia e horário!");
        }

        // setando data de criação manualmente
        agendamento.setCriadoEm(Timestamp.valueOf(LocalDateTime.now()));
        agendamentoRepo.save(agendamento);

        return ResponseEntity.status(201).body(agendamento);
    }

    // Buscar agendamento por ID
    @GetMapping("/buscarAgendamento/{id}")
    public Optional<agendamentos> buscarPorId(@PathVariable("id") int id) {
        return agendamentoRepo.findById(id);
    }

    // Buscar por data e hora (usando tipos corretos)
    @GetMapping("/buscarPorDataHora")
    public List<agendamentos> buscarPorDataHora(
            @RequestParam("data") String data, // "yyyy-MM-dd"
            @RequestParam("hora") String hora // "HH:mm:ss"
    ) {
        Date dataSql = Date.valueOf(data);
        Time horaSql = Time.valueOf(hora);
        return agendamentoRepo.findByDataAndHora(dataSql, horaSql);
    }

    @PutMapping("/atualizarAgendamento")
    public void atualizarAgendamento(@RequestBody agendamentos agendamento) {
        agendamentoRepo.save(agendamento);
    }

    @DeleteMapping("/removerAgendamento/{id}")
    public void removerPorId(@PathVariable("id") int id) {
        agendamentoRepo.deleteById(id);
    }

    // NOVAS BUSCAS
    @GetMapping("/buscarPorServico/{servico}")
    public List<agendamentos> buscarPorServico(@PathVariable("servico") String servico) {
        return agendamentoRepo.findByServico(servico);
    }

    @GetMapping("/buscarPorUsuario/{usuarioId}")
    public List<agendamentos> buscarPorUsuarioId(@PathVariable("usuarioId") int usuarioId) {
        return agendamentoRepo.findByUsuarioId(usuarioId);
    }

    @GetMapping("/buscarPorStatus/{status}")
    public List<agendamentos> buscarPorStatus(@PathVariable("status") agendamentos.Status status) {
        return agendamentoRepo.findByStatus(status);
    }

@PutMapping("/atualizarStatus/{id}")
public ResponseEntity<String> atualizarStatus(@PathVariable("id") int id, @RequestBody Map<String, String> request) {
    String statusRecebido = request.get("status");
    String motivo = request.get("motivoCancelamento"); // campo obrigatório para cancelamento

    Optional<agendamentos> agendamentoOpt = agendamentoRepo.findById(id);
    if (!agendamentoOpt.isPresent()) {
        return ResponseEntity.status(404).body("Agendamento não encontrado.");
    }

    agendamentos agendamento = agendamentoOpt.get();
    agendamentos.Status novoStatus;

    switch (statusRecebido.toLowerCase()) {
        case "pendente":
            novoStatus = agendamentos.Status.pendente;
            break;
        case "confirmado":
            novoStatus = agendamentos.Status.confirmado;
            break;
        case "concluido":
            novoStatus = agendamentos.Status.concluido;
            break;
        case "cancelado":
            novoStatus = agendamentos.Status.cancelado;
            break;
        default:
            return ResponseEntity.badRequest().body("Status inválido.");
    }

    // ⚠️ Validação obrigatória do motivo para cancelamento
    if (novoStatus == agendamentos.Status.cancelado) {
        if (motivo == null || motivo.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Motivo do cancelamento é obrigatório.");
        }
        agendamento.setMotivo(motivo.trim());
    }

    agendamento.setStatus(novoStatus);
    agendamentoRepo.save(agendamento);
    return ResponseEntity.ok("Status atualizado com sucesso!");
}

}
