package co.edu.unbosque.proyectoFinalC3.util;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Clase utilitaria para operaciones criptográficas.
 * <p>
 * Proporciona métodos para:
 * <ul>
 * <li>Cifrado y descifrado AES-GCM</li>
 * <li>Generación de hashes (MD5, SHA-1, SHA-256, SHA-384, SHA-512)</li>
 * <li>Codificación Base64</li>
 * </ul>
 * </p>
 */
public class AESUtil {

	private static final String ALGORITMO = "AES";

	private static final String TIPOCIFRADO = "AES/GCM/NoPadding";

	/**
	 * Cifra un texto usando AES-GCM.
	 * 
	 * @param llave Clave de cifrado (debe ser de 16, 24 o 32 bytes)
	 * @param iv    Vector de inicialización (debe ser de 16 bytes)
	 * @param texto Texto a cifrar
	 * @return Texto cifrado en Base64
	 */
	public static String encrypt(String llave, String iv, String texto) {
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(TIPOCIFRADO);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}

		SecretKeySpec secretKeySpec = new SecretKeySpec(llave.getBytes(), ALGORITMO);
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv.getBytes());
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}

		byte[] encrypted = null;
		try {
			encrypted = cipher.doFinal(texto.getBytes());
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}

		return new String(encodeBase64(encrypted));
	}

	/**
	 * Descifra un texto usando AES-GCM.
	 * 
	 * @param llave     Clave de cifrado (debe coincidir con la usada para cifrar)
	 * @param iv        Vector de inicialización (debe coincidir con el usado para
	 *                  cifrar)
	 * @param encrypted Texto cifrado en Base64
	 * @return Texto descifrado
	 */
	public static String decrypt(String llave, String iv, String encrypted) {
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(TIPOCIFRADO);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}

		SecretKeySpec secretKeySpec = new SecretKeySpec(llave.getBytes(), ALGORITMO);
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv.getBytes());
		try {
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {

			e.printStackTrace();
		}

		byte[] enc = decodeBase64(encrypted);
		byte[] decrypted = null;
		try {
			decrypted = cipher.doFinal(enc);
			return new String(decrypted);
		} catch (IllegalBlockSizeException | BadPaddingException e) {

			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Descifra un texto usando claves por defecto.
	 * 
	 * @param encrypted Texto cifrado en Base64
	 * @return Texto descifrado
	 */
	public static String decrypt(String encrypted) {
		String iv = "notengoniideatre";
		String key = "vodkadeluloyoron";
		return decrypt(key, iv, encrypted);
	}

    /**
     * Cifra un texto usando claves por defecto.
     * 
     * @param plainText Texto a cifrar
     * @return Texto cifrado en Base64
     */
	public static String encrypt(String plainText) {
		String iv = "notengoniideatre";
		String key = "vodkadeluloyoron";
		return encrypt(key, iv, plainText);
	}

    /**
     * Genera hash MD5 de un contenido.
     * 
     * @param content Contenido a hashear
     * @return Hash MD5 en hexadecimal
     */
	public static String hashingToMD5(String content) {
		return DigestUtils.md5Hex(content);
	}

    /**
     * Genera hash SHA-1 de un contenido.
     * 
     * @param content Contenido a hashear
     * @return Hash SHA-1 en hexadecimal
     */
	public static String hashingToSHA1(String content) {
		return DigestUtils.sha1Hex(content);
	}

    /**
     * Genera hash SHA-256 de un contenido.
     * 
     * @param content Contenido a hashear
     * @return Hash SHA-256 en hexadecimal
     */
	public static String hashingToSHA256(String content) {
		return DigestUtils.sha256Hex(content);
	}

    /**
     * Genera hash SHA-384 de un contenido.
     * 
     * @param content Contenido a hashear
     * @return Hash SHA-384 en hexadecimal
     */
	public static String hashingToSHA384(String content) {
		return DigestUtils.sha384Hex(content);
	}

    /**
     * Genera hash SHA-512 de un contenido.
     * 
     * @param content Contenido a hashear
     * @return Hash SHA-512 en hexadecimal
     */
	public static String hashingToSHA512(String content) {
		return DigestUtils.sha512Hex(content);
	}

}
