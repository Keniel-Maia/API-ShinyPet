package com.example.APIShinyPET.Controller;

import com.example.APIShinyPET.Models.Precos;
import com.example.APIShinyPET.Repository.PrecosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/apiPrecos")
public class PrecosController {

    @Autowired
    private PrecosRepository precosRepository;

    private String getPorteById(int id) {
        Set<Integer> idsPequeno = Set.of(1, 2, 3, 4, 5, 16);
        Set<Integer> idsMedio = Set.of(6, 7, 8, 9, 10, 17);
        Set<Integer> idsGrande = Set.of(11, 12, 13, 14, 15, 18);

        if (idsPequeno.contains(id)) return "Pequeno";
        if (idsMedio.contains(id)) return "Médio";
        if (idsGrande.contains(id)) return "Grande";
        return "Desconhecido";
    }


    @GetMapping("/listarPrecos")
    public List<Precos> listarTodosPrecos() {
        return precosRepository.findAll();
    }


    @PostMapping("/inserirPreco")
    public ResponseEntity<Map<String, Object>> inserirPreco(@RequestBody Precos preco) {
        precosRepository.save(preco);
        return buscarPorId(preco.getId());
    }


    @GetMapping("/buscarPreco/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable("id") int id) {
        Optional<Precos> preco = precosRepository.findById(id);
        if (preco.isEmpty()) return ResponseEntity.notFound().build();

        Map<String, Object> resp = new HashMap<>();
        resp.put("preco", preco.get());
        resp.put("porte", getPorteById(id)); // Adiciona porte calculado
        return ResponseEntity.ok(resp);
    }


    @PutMapping("/atualizarPreco")
    public void atualizarPreco(@RequestBody Precos preco) {
        precosRepository.save(preco);
    }


    @DeleteMapping("/removerPreco/{id}")
    public void removerPorId(@PathVariable("id") int id) {
        precosRepository.deleteById(id);
    }


    @GetMapping("/buscarPorTamanho/{tamanho}")
    public List<Precos> buscarPorTamanho(@PathVariable("tamanho") String tamanho) {
        return precosRepository.findByTamanho(tamanho);
    }



    @GetMapping("/buscarPorServico/{servico}")
    public List<Precos> buscarPorServico(@PathVariable("servico") String servico) {
        return precosRepository.findByServico(servico);
    }
}
