package com.example.APIShinyPET.service;

import com.example.APIShinyPET.Models.Usuario;
import com.example.APIShinyPET.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class RecuperacaoSenhaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final WebClient webClient;

    @Value("${resend.api.key}")
    private String resendApiKey;

    public RecuperacaoSenhaService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.resend.com").build();
    }

    @Async
    public void enviarTokenPorEmail(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            String token = gerarToken();
            LocalDateTime expira = LocalDateTime.now().plusMinutes(30);

            usuario.setToken(token);
            usuario.setTokenExpira(expira);
            usuarioRepository.save(usuario);

            String corpoHtml = String.format("""
                <div style="font-family: Josefin Sans, sans-serif; background-color: #f7f1f1; padding: 20px; border-radius: 12px; border: 2px solid #DA81CE; max-width: 600px; margin: auto;">
                    <div style="text-align: center;">
                        <h2 style="color: #A591F2;">Redefinição de Senha</h2>
                        <p style="color: #7c8595; font-size: 16px; line-height: 1.5;">
                            Recebemos uma solicitação para redefinir sua senha no <strong>Shiny Pet</strong>.<br><br>
                            Aqui está seu token de recuperação:<br>
                            <strong style="font-size: 18px; color: #A591F2;">%s</strong><br><br>
                            Esse token é válido por 30 minutos.<br>
                            Copie e cole no aplicativo para continuar o processo de redefinição.<br><br>
                            Com carinho,<br>
                            Equipe Shiny Pet
                        </p>
                    </div>
                </div>
            """, token);

            Map<String, Object> payload = Map.of(
                "from", "Shiny Pet <onboarding@resend.dev>",
                "to", email,
                "subject", "Shiny Pet - Redefinição de Senha",
                "html", corpoHtml
            );

            webClient.post()
                .uri("/emails")
                .header("Authorization", "Bearer " + resendApiKey)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> System.out.println("Token enviado por e-mail: " + response));
        }
    }

    public boolean validarToken(String token) {
        return usuarioRepository.findByTokenAndTokenExpiraAfter(token, LocalDateTime.now()).isPresent();
    }

    public boolean redefinirSenha(String token, String novaSenhaCriptografada) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByTokenAndTokenExpiraAfter(token, LocalDateTime.now());
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setSenha(novaSenhaCriptografada);
            usuario.setToken(null);
            usuario.setTokenExpira(null);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    private String gerarToken() {
        SecureRandom random = new SecureRandom();
        int token = 100_000_000 + random.nextInt(900_000_000); // Garante 9 dígitos
        return String.valueOf(token);
    }
}
