package com.example.APIShinyPET.Repository;

import com.example.APIShinyPET.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNome(String nome);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmailAndSenha(String email, String senha);

    @Query("SELECT u FROM Usuario u WHERE u.nivel = :nivel")
    List<Usuario> buscarUsuariosPorNivel(int nivel);


    Optional<Usuario> findByTokenAndTokenExpiraAfter(String token, LocalDateTime agora);

}
