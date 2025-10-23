package com.example.APIShinyPET.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private SendGridService sendGridService;

    public void enviarEmailCadastro(String nomeCompleto, String email) {
        String corpoHtml = String.format("""
            <div style="font-family: Josefin Sans, sans-serif; background-color: #f7f1f1; padding: 20px; border-radius: 12px; border: 2px solid #DA81CE; max-width: 600px; margin: auto;">
                <div style="text-align: center;">
                    <img src="https://lemundo.com.br/wp-content/uploads/2023/07/cachorro-feliz.webp" alt="Logo Shiny Pet" style="width: 80px; height: 80px; border-radius: 50%%; border: 3px solid #f4a2e9; object-fit: cover; margin-bottom: 15px;">
                    <h2 style="color: #A591F2;">Cadastro realizado com sucesso!</h2>
                    <p style="color: #7c8595; font-size: 16px; line-height: 1.5;">
                        Olá %s, <br><br>
                        Seu cadastro no <strong>Shiny Pet</strong> foi concluído com sucesso.<br>
                        Agora você pode fazer login e aproveitar todos os recursos do nosso sistema!<br><br>
                        Com carinho,<br>
                        Equipe Shiny Pet
                    </p>
                </div>
            </div>
        """, nomeCompleto);

        try {
            sendGridService.enviarEmail(email, "Cadastro realizado com sucesso", corpoHtml);
        } catch (IOException e) {
            System.out.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
