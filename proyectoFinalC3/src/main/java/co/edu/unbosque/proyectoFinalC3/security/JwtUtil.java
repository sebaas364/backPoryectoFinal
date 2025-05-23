package co.edu.unbosque.proyectoFinalC3.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import co.edu.unbosque.proyectoFinalC3.model.Usuario;

/**
 * Utilidad para manejo de tokens JWT (JSON Web Tokens).
 * <p>
 * Esta clase proporciona métodos para:
 * <ul>
 *   <li>Generar tokens JWT</li>
 *   <li>Extraer información de tokens</li>
 *   <li>Validar tokens</li>
 * </ul>
 * </p>
 * <p>
 * Los tokens generados incluyen:
 * <ul>
 *   <li>Nombre de usuario como subject</li>
 *   <li>Roles/autoridades del usuario</li>
 *   <li>Fecha de expiración</li>
 * </ul>
 * </p>
 */
@Component
public class JwtUtil {

    /**
     * Tiempo de validez del token en milisegundos (1 hora)
     */
    private static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60 * 1000;

    /**
     * Clave secreta para firmar los tokens, configurable mediante properties
     */
    @Value("${jwt.secret:defaultSecretKeyWhichShouldBeAtLeast32CharactersLong}")
    private String secret;

    /**
     * Obtiene la clave de firma para los tokens JWT.
     * 
     * @return Clave de firma generada a partir del secreto configurado
     */
    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extrae el nombre de usuario (subject) del token.
     * 
     * @param token Token JWT
     * @return Nombre de usuario contenido en el token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae la fecha de expiración del token.
     * 
     * @param token Token JWT
     * @return Fecha de expiración del token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae el rol del usuario del token.
     * 
     * @param token Token JWT
     * @return Rol del usuario contenido en el token
     */
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    /**
     * Extrae un claim específico del token.
     * 
     * @param <T> Tipo del claim a extraer
     * @param token Token JWT
     * @param claimsResolver Función para extraer el claim específico
     * @return Valor del claim solicitado
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims del token.
     * 
     * @param token Token JWT
     * @return Todos los claims contenidos en el token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Verifica si el token ha expirado.
     * 
     * @param token Token JWT
     * @return true si el token ha expirado, false en caso contrario
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Genera un nuevo token JWT para un usuario.
     * 
     * @param userDetails Detalles del usuario para incluir en el token
     * @return Token JWT generado
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        if (userDetails instanceof Usuario) {
            Usuario user = (Usuario) userDetails;
            claims.put("role", user.getRole().name());
        }
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Crea un token JWT con los claims especificados.
     * 
     * @param claims Claims a incluir en el token
     * @param subject Subject (usualmente nombre de usuario) del token
     * @return Token JWT generado
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida si un token es válido para un usuario específico.
     * 
     * @param token Token JWT a validar
     * @param userDetails Detalles del usuario contra el que validar el token
     * @return true si el token es válido para el usuario, false en caso contrario
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
}
