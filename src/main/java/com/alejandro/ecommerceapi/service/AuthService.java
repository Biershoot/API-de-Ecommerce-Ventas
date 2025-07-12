package com.alejandro.ecommerceapi.service;

import com.alejandro.ecommerceapi.dto.LoginRequest;
import com.alejandro.ecommerceapi.dto.RegisterRequest;
import com.alejandro.ecommerceapi.dto.AuthResponse;
import com.alejandro.ecommerceapi.entity.User;
import com.alejandro.ecommerceapi.repository.UserRepository;
import com.alejandro.ecommerceapi.security.CustomUserDetails;
import com.alejandro.ecommerceapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Clase de servicio para manejar operaciones de autenticación y registro de usuarios.
 * 
 * <p>Este servicio proporciona métodos para funcionalidad de registro e inicio de sesión de usuarios.
 * Maneja la encriptación de contraseñas, generación de tokens JWT y autenticación de usuarios
 * usando el administrador de autenticación de Spring Security.</p>
 * 
 * <p>El servicio se integra con los siguientes componentes:
 * <ul>
 *   <li>{@link UserRepository} - Para persistencia de datos de usuarios</li>
 *   <li>{@link PasswordEncoder} - Para encriptación de contraseñas</li>
 *   <li>{@link JwtService} - Para generación de tokens JWT</li>
 *   <li>{@link AuthenticationManager} - Para autenticación de usuarios</li>
 * </ul>
 * </p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * <p>Este método crea una nueva cuenta de usuario con la información proporcionada.
     * La contraseña se encripta antes de almacenar en la base de datos. Después de un registro
     * exitoso, se genera un token JWT y se devuelve para autenticación inmediata.</p>
     * 
     * @param request La solicitud de registro que contiene los detalles del usuario
     * @return Un AuthResponse que contiene el token JWT para el usuario recién registrado
     * @throws RuntimeException si el email ya está en uso
     */
    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }

    /**
     * Autentica un usuario y genera un token JWT.
     * 
     * <p>Este método valida las credenciales del usuario usando el administrador de
     * autenticación de Spring Security. Si las credenciales son válidas, se genera
     * un token JWT y se devuelve para solicitudes posteriores de la API.</p>
     * 
     * @param request La solicitud de inicio de sesión que contiene email y contraseña
     * @return Un AuthResponse que contiene el token JWT para el usuario autenticado
     * @throws RuntimeException si las credenciales son inválidas o el usuario no se encuentra
     */
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}

