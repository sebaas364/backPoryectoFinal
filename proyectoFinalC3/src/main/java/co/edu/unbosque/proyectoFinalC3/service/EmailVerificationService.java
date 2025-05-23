package co.edu.unbosque.proyectoFinalC3.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servicio para el envío y generación de códigos de verificación por correo electrónico.
 * <p>
 * Esta clase se encarga de:
 * <ul>
 *   <li>Generar códigos de verificación aleatorios</li>
 *   <li>Enviar códigos de verificación por correo electrónico</li>
 * </ul>
 * </p>
 */
@Service
public class EmailVerificationService {

	private final JavaMailSender mailSender;

	private static final int CODE_LENGTH = 6;

    /**
     * Constructor que inyecta el servicio de envío de correos.
     * 
     * @param mailSender Servicio para enviar correos electrónicos
     */
	public EmailVerificationService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

    /**
     * Envía un código de verificación al correo electrónico especificado.
     * 
     * @param toEmail Dirección de correo electrónico del destinatario
     * @return El código de verificación generado y enviado
     */
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

    /**
     * Genera un código numérico aleatorio de longitud fija.
     * 
     * @return Código de verificación generado
     */
	private String generateRandomCode() {
		int code = (int) (Math.random() * Math.pow(10, CODE_LENGTH));
		return String.format("%0" + CODE_LENGTH + "d", code);
	}
	
}
