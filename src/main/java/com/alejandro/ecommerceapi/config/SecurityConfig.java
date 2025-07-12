package com.alejandro.ecommerceapi.config;

import com.alejandro.ecommerceapi.repository.UserRepository;
import com.alejandro.ecommerceapi.security.CustomUserDetails;
import com.alejandro.ecommerceapi.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase de configuración para Spring Security.
 * 
 * <p>Esta clase configura toda la seguridad de la aplicación Spring Boot, incluyendo:
 * <ul>
 *   <li>Configuración de filtros de seguridad</li>
 *   <li>Configuración de autenticación y autorización</li>
 *   <li>Configuración de codificación de contraseñas</li>
 *   <li>Configuración de endpoints públicos y protegidos</li>
 *   <li>Integración con JWT para autenticación stateless</li>
 * </ul>
 * </p>
 * 
 * <p>La configuración establece:
 * <ul>
 *   <li>Endpoints de autenticación como públicos</li>
 *   <li>Endpoints de productos como públicos para lectura</li>
 *   <li>Endpoints de pedidos y gestión como protegidos</li>
 *   <li>Autenticación basada en JWT sin sesiones</li>
 *   <li>Codificación de contraseñas con BCrypt</li>
 * </ul>
 * </p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserRepository userRepository;

    /**
     * Configura la cadena de filtros de seguridad para la aplicación.
     * 
     * <p>Este método define:
     * <ul>
     *   <li>Endpoints que requieren autenticación vs. endpoints públicos</li>
     *   <li>Configuración de sesiones (stateless para JWT)</li>
     *   <li>Configuración de CORS</li>
     *   <li>Integración del filtro JWT personalizado</li>
     *   <li>Configuración del proveedor de autenticación</li>
     * </ul>
     * </p>
     * 
     * @param http El objeto HttpSecurity para configurar
     * @return SecurityFilterChain configurado
     * @throws Exception si ocurre un error durante la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/products/**").permitAll()
                        .requestMatchers("/api/orders/**").authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configura el proveedor de autenticación personalizado.
     * 
     * <p>Este bean configura cómo se autentican los usuarios, incluyendo:
     * <ul>
     *   <li>El servicio de detalles de usuario personalizado</li>
     *   <li>El codificador de contraseñas BCrypt</li>
     * </ul>
     * </p>
     * 
     * @return AuthenticationProvider configurado
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configura el administrador de autenticación.
     * 
     * <p>Este bean proporciona el AuthenticationManager que se utiliza
     * para autenticar usuarios durante el proceso de inicio de sesión.</p>
     * 
     * @param config La configuración de autenticación
     * @return AuthenticationManager configurado
     * @throws Exception si ocurre un error durante la configuración
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configura el servicio de detalles de usuario personalizado.
     * 
     * <p>Este bean define cómo se cargan los detalles del usuario desde la base de datos.
     * Utiliza el repositorio de usuarios para buscar usuarios por email y los convierte
     * a objetos CustomUserDetails para Spring Security.</p>
     * 
     * @return UserDetailsService configurado
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    /**
     * Configura el codificador de contraseñas BCrypt.
     * 
     * <p>Este bean proporciona el PasswordEncoder que se utiliza para encriptar
     * contraseñas antes de almacenarlas en la base de datos y para verificar
     * contraseñas durante la autenticación.</p>
     * 
     * @return PasswordEncoder configurado con BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

