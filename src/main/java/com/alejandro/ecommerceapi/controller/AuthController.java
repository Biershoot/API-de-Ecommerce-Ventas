package com.alejandro.ecommerceapi.controller;

import com.alejandro.ecommerceapi.dto.AuthResponse;
import com.alejandro.ecommerceapi.dto.LoginRequest;
import com.alejandro.ecommerceapi.dto.RegisterRequest;
import com.alejandro.ecommerceapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para operaciones de autenticación y registro de usuarios.
 * 
 * <p>Este controlador proporciona endpoints para:
 * <ul>
 *   <li>Registro de nuevos usuarios</li>
 *   <li>Inicio de sesión de usuarios existentes</li>
 *   <li>Generación de tokens JWT para autenticación</li>
 * </ul>
 * </p>
 * 
 * <p>Todos los endpoints de este controlador son públicos y no requieren autenticación.
 * Los tokens JWT generados deben incluirse en el header "Authorization" para
 * acceder a endpoints protegidos.</p>
 * 
 * <p>Base URL: {@code /api/auth}</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * <p>Este endpoint permite crear una nueva cuenta de usuario con la información proporcionada.
     * La contraseña se encripta automáticamente antes de almacenar en la base de datos.
     * Después de un registro exitoso, se devuelve un token JWT para autenticación inmediata.</p>
     * 
     * <p>Ejemplo de solicitud:</p>
     * <pre>
     * {
     *   "name": "Juan Pérez",
     *   "email": "juan@example.com",
     *   "password": "contraseña123",
     *   "role": "CLIENT"
     * }
     * </pre>
     * 
     * @param request La solicitud de registro que contiene los detalles del usuario
     * @return ResponseEntity con el token JWT generado
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Autentica un usuario y genera un token JWT.
     * 
     * <p>Este endpoint valida las credenciales del usuario y, si son correctas,
     * genera un token JWT que puede usarse para acceder a endpoints protegidos.
     * El token debe incluirse en el header "Authorization" con el formato
     * "Bearer {token}".</p>
     * 
     * <p>Ejemplo de solicitud:</p>
     * <pre>
     * {
     *   "email": "juan@example.com",
     *   "password": "contraseña123"
     * }
     * </pre>
     * 
     * @param request La solicitud de inicio de sesión que contiene email y contraseña
     * @return ResponseEntity con el token JWT generado
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}

