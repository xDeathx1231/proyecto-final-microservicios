package com.ef.banner.usuarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;

	public void enviarCorreoRecuperacion(String to, String token) {
		
		
		String link = "http://localhost:5500/reset-password.html?token=" + token;  

		SimpleMailMessage mensaje = new SimpleMailMessage();
		mensaje.setTo(to);
		mensaje.setSubject("Recuperación de contraseña");
		mensaje.setText("Haz clic en el siguiente enlace para restablecer tu contraseña:\n" + link);

		mailSender.send(mensaje);
		
		
	}
	
    public void enviarCorreo(String para, String asunto, String contenido) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(para);
        mensaje.setSubject(asunto);
        mensaje.setText(contenido);
        mailSender.send(mensaje);
    }
}
