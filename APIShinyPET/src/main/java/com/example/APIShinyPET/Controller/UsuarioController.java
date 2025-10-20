package com.example.APIShinyPET.Controller;
import com.example.APIShinyPET.Models.Usuario;
import com.example.APIShinyPET.Repository.UsuarioRepository;
import com.example.APIShinyPET.service.EmailService;
import com.example.APIShinyPET.service.RecuperacaoSenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/apiUsuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private EmailService emailService;


    @GetMapping("/buscarUsuarios")
    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepo.findAll();
    }


    @GetMapping("/buscarPorNivel/{nivel}")
    public List<Usuario> buscarUsuariosPorNivel(@PathVariable int nivel) {
        return usuarioRepo.buscarUsuariosPorNivel(nivel);
    }


    @PostMapping("/inserirUsuario")
    public Usuario inserirUsuario(@RequestBody Usuario usuario) {
        System.out.println("JSON RECEBIDO: " + usuario);
        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setNivel(0);

        // Salva o usuário
        Usuario usuarioSalvo = usuarioRepo.save(usuario);

        // Envia o e-mail de boas-vindas
        emailService.enviarEmailCadastro(usuarioSalvo.getNome(), usuarioSalvo.getEmail());

        return usuarioSalvo;
    }


    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario usuario) {
        Optional<Usuario> optUser = usuarioRepo.findByEmail(usuario.getEmail());
        if (optUser.isPresent()) {
            Usuario user = optUser.get();
            // Compara a senha digitada (plain) com o hash no banco

            }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha incorretos");
    }

    @GetMapping("/buscarUsuarioId/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable("id") int id) {
        Optional<Usuario> usuarioOptional = usuarioRepo.findById((long) id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/atualizarUsuario")
    public void atualizarUsuario(@RequestBody Usuario usuario) {
        usuarioRepo.save(usuario);
    }

    @DeleteMapping("/removerUsuario/{id}")
    public void removerPorId(@PathVariable("id") int id) {
        usuarioRepo.deleteById((long) id);
    }

    @GetMapping("/buscarPorNome/{nome}")
    public List<Usuario> buscarPorNome(@PathVariable("nome") String nome) {
        return usuarioRepo.findByNome(nome);
    }

    @GetMapping("/buscarPorEmail/{email}")
    public Optional<Usuario> buscarPorEmail(@PathVariable("email") String email) {
        return usuarioRepo.findByEmail(email);
    }

    // ====== NOVOS ENDPOINTS PARA /findEmail ======
    // 1. GET usando query string: /apiUsuarios/findEmail?email=joao@gmail.com
    @GetMapping("/findEmail")
    public Optional<Usuario> findEmailGet(@RequestParam String email) {
        return usuarioRepo.findByEmail(email);
    }

    // 2. POST usando JSON: { "email": "joao@gmail.com" }
    @PostMapping("/findEmail")
    public Optional<Usuario> findEmailPost(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        return usuarioRepo.findByEmail(email);
    }


    @Autowired
    private RecuperacaoSenhaService recuperacaoSenhaService;

    @PostMapping("/recuperar-senha")
    public ResponseEntity<?> recuperarSenha(@RequestParam String email) {
        recuperacaoSenhaService.enviarTokenPorEmail(email);
        return ResponseEntity.ok("Se o e-mail existir, um token foi enviado.");
    }

    @GetMapping("/validar-token")
    public ResponseEntity<?> validarToken(@RequestParam String token) {
        boolean valido = recuperacaoSenhaService.validarToken(token);
        if (valido) {
            return ResponseEntity.ok("Token válido");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }
    }


    @PostMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(@RequestParam String token, @RequestParam String novaSenhaCriptografada) {
        if (novaSenhaCriptografada.length() < 6) {
            return ResponseEntity.badRequest().body("A senha deve ter pelo menos 6 caracteres.");
        }

        boolean sucesso = recuperacaoSenhaService.redefinirSenha(token, novaSenhaCriptografada);
        if (sucesso) {
            return ResponseEntity.ok("Senha redefinida com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }
    }


}
