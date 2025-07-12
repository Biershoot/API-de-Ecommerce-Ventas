package com.alejandro.ecommerceapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro JWT personalizado para interceptar y procesar tokens JWT en solicitudes HTTP.
 * 
 * <p>Este filtro se ejecuta una vez por solicitud y es responsable de:
 * <ul>
 *   <li>Extraer tokens JWT del header de Authorization</li>
 *   <li>Validar tokens y extraer información del usuario</li>
 *   <li>Establecer el contexto de autenticación de Spring Security</li>
 *   <li>Permitir que solicitudes sin token pasen a través del filtro</li>
 * </ul>
 * </p>
 * 
 * <p>El filtro se integra con {@link JwtService} para validación de tokens y
 * {@link UserDetailsService} para cargar detalles del usuario.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Procesa cada solicitud HTTP para extraer y validar tokens JWT.
     * 
     * <p>Este método intercepta cada solicitud HTTP y:
     * <ol>
     *   <li>Extrae el token JWT del header "Authorization"</li>
     *   <li>Valida el token usando JwtService</li>
     *   <li>Carga los detalles del usuario si el token es válido</li>
     *   <li>Establece el contexto de autenticación de Spring Security</li>
     *   <li>Continúa con la cadena de filtros</li>
     * </ol>
     * </p>
     * 
     * <p>Si no se encuentra un token o el token es inválido, la solicitud
     * continúa sin autenticación establecida.</p>
     * 
     * @param request La solicitud HTTP entrante
     * @param response La respuesta HTTP saliente
     * @param filterChain La cadena de filtros para continuar el procesamiento
     * @throws ServletException si ocurre un error durante el procesamiento
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Verificar si el header de Authorization existe y tiene el formato correcto
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token JWT del header
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        // Si se extrajo un email y no hay autenticación establecida, validar el token
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            
            // Validar el token contra los detalles del usuario
            if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}

