package co.edu.unbosque.proyectoFinalC3.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {

	private final JavaMailSender mailSender;

	private static final int CODE_LENGTH = 6;

	public EmailVerificationService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String sendVerificationCode(String toEmail) {
		String verificationCode = generateRandomCode();
		String subject = "Tu código de verificación";
		String message = "Tu código de verificación es: " + verificationCode
				+ "\n\nEste código expirará en 10 minutos.";
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(toEmail);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		mailMessage.setFrom("autentificacion5@gmail.com");
		mailSender.send(mailMessage);
		return verificationCode;
	}

	private String generateRandomCode() {
		int code = (int) (Math.random() * Math.pow(10, CODE_LENGTH));
		return String.format("%0" + CODE_LENGTH + "d", code);
	}
	
}
