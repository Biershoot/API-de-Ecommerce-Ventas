package com.alejandro.ecommerceapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Clase de servicio para manejar operaciones JWT (JSON Web Token).
 * 
 * <p>Este servicio proporciona funcionalidad JWT completa incluyendo generación de tokens,
 * validación y extracción de claims. Utiliza la biblioteca JJWT para manejar todas las
 * operaciones relacionadas con JWT usando el algoritmo de firma HMAC-SHA256.</p>
 * 
 * <p>El servicio soporta:
 * <ul>
 *   <li>Generación de tokens con claims personalizados</li>
 *   <li>Extracción de nombres de usuario desde tokens</li>
 *   <li>Validación de tokens y verificación de expiración</li>
 *   <li>Extracción de claims personalizados</li>
 * </ul>
 * </p>
 * 
 * <p>Los tokens están configurados con un período de expiración de 24 horas y usan una
 * clave secreta segura para la firma.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Service
public class JwtService {

    /**
     * Clave secreta utilizada para firmar y verificar tokens JWT.
     * En producción, esto debería ser externalizado a la configuración.
     */
    private static final String SECRET_KEY = "Mb8PZ7vB3Lm9Jn2Xq5Rt7Ks1Yp4Dw6Fg8Hj0Ak3Ve9Zo2Xc1";

    /**
     * Extrae el nombre de usuario (subject) de un token JWT.
     * 
     * @param token El token JWT del cual extraer el nombre de usuario
     * @return El nombre de usuario contenido en el token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae un claim específico de un token JWT usando una función de resolución personalizada.
     * 
     * @param token El token JWT del cual extraer el claim
     * @param resolver Función para extraer el claim específico
     * @param <T> El tipo del claim a extraer
     * @return El valor del claim extraído
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Genera un token JWT para un nombre de usuario con claims por defecto.
     * 
     * @param username El nombre de usuario a incluir en el token
     * @return El token JWT generado
     */
    public String generateToken(String username) {
        return generateToken(new HashMap<>(), username);
    }

    /**
     * Genera un token JWT para un nombre de usuario con claims extra personalizados.
     * 
     * @param extraClaims Claims adicionales a incluir en el token
     * @param username El nombre de usuario a incluir en el token
     * @return El token JWT generado
     */
    public String generateToken(Map<String, Object> extraClaims, String username) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Genera un token JWT para un objeto UserDetails con claims por defecto.
     * 
     * @param userDetails El objeto UserDetails que contiene información del usuario
     * @return El token JWT generado
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un token JWT para un objeto UserDetails con claims extra personalizados.
     * 
     * @param extraClaims Claims adicionales a incluir en el token
     * @param userDetails El objeto UserDetails que contiene información del usuario
     * @return El token JWT generado
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida un token JWT contra un nombre de usuario.
     * 
     * <p>Este método verifica si el token es válido comprobando:
     * <ul>
     *   <li>El subject del token coincide con el nombre de usuario proporcionado</li>
     *   <li>El token no ha expirado</li>
     * </ul>
     * </p>
     * 
     * @param token El token JWT a validar
     * @param username El nombre de usuario contra el cual validar
     * @return true si el token es válido, false en caso contrario
     */
    public boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    /**
     * Verifica si un token JWT ha expirado.
     * 
     * @param token El token JWT a verificar
     * @return true si el token ha expirado, false en caso contrario
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    /**
     * Extrae todos los claims de un token JWT.
     * 
     * @param token El token JWT del cual extraer claims
     * @return El objeto Claims que contiene todos los claims del token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtiene la clave de firma utilizada para operaciones JWT.
     * 
     * @return El objeto Key utilizado para firmar y verificar tokens
     */
    private Key getSignInKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
