package com.example.APIShinyPET.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void enviarEmailCadastro(String nomeCompleto, String email) {
        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Cadastro realizado com sucesso");
            helper.setFrom("shinypetcotil@gmail.com");

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

            helper.setText(corpoHtml, true);
            mailSender.send(mensagem);

        } catch (Exception e) {
            e.printStackTrace(); // ou use um logger se preferir
        }
    }
}
