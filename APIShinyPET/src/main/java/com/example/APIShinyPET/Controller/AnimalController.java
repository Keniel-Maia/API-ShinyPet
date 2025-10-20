package com.example.APIShinyPET.Controller;

import com.example.APIShinyPET.Models.Animal;
import com.example.APIShinyPET.Repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/apiAnimais")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;


    @GetMapping("/listarAnimais")
    public List<Animal> listarTodosAnimais() {
        return animalRepository.findAll();
    }


    @PostMapping("/inserirAnimal")
    public Optional<Animal> inserirAnimal(@RequestBody Animal animal) {
        animalRepository.save(animal);
        return buscarPorId(animal.getIdAnimal());
    }


    @GetMapping("/buscarAnimal/{id}")
    public Optional<Animal> buscarPorId(@PathVariable("id") int id) {
        return animalRepository.findById(id);
    }


    @PutMapping("/atualizarAnimal")
    public void atualizarAnimal(@RequestBody Animal animal) {
        animalRepository.save(animal);
    }


    @DeleteMapping("/removerAnimal/{id}")
    public void removerPorId(@PathVariable("id") int id) {
        animalRepository.deleteById(id);
    }


    @GetMapping("/buscarPorNome/{nome}")
    public List<Animal> buscarPorNome(@PathVariable("nome") String nome) {
        return animalRepository.findByNome(nome);
    }


    @GetMapping("/buscarPorUsuarioID/{usuarioID}")
    public List<Animal> buscarPorUsuarioID(@PathVariable("usuarioID") int usuarioID) {
        return animalRepository.findByUsuarioId(usuarioID);
    }
}
