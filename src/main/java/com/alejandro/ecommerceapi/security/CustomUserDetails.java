package com.alejandro.ecommerceapi.security;

import com.alejandro.ecommerceapi.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Clase personalizada que implementa UserDetails para integrar la entidad User con Spring Security.
 * 
 * <p>Esta clase actúa como un adaptador entre la entidad User del dominio y los requerimientos
 * de Spring Security. Proporciona la información necesaria para la autenticación y autorización,
 * incluyendo credenciales, autoridades y estado de la cuenta.</p>
 * 
 * <p>La clase convierte el rol de la entidad User en una autoridad de Spring Security
 * y proporciona acceso a los detalles del usuario para el contexto de seguridad.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    /**
     * Devuelve las autoridades (roles) del usuario.
     * 
     * <p>Este método convierte el rol de la entidad User en una autoridad de Spring Security.
     * El rol se convierte a un formato que Spring Security puede entender para
     * decisiones de autorización.</p>
     * 
     * @return Una colección de autoridades que representa los roles del usuario
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    /**
     * Devuelve la contraseña del usuario.
     * 
     * @return La contraseña encriptada del usuario
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Devuelve el nombre de usuario (email) del usuario.
     * 
     * @return El email del usuario que se usa como nombre de usuario
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Indica si la cuenta del usuario no ha expirado.
     * 
     * <p>En esta implementación, siempre devuelve true ya que no se implementa
     * funcionalidad de expiración de cuentas.</p>
     * 
     * @return true si la cuenta no ha expirado, false en caso contrario
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta del usuario no está bloqueada.
     * 
     * <p>En esta implementación, siempre devuelve true ya que no se implementa
     * funcionalidad de bloqueo de cuentas.</p>
     * 
     * @return true si la cuenta no está bloqueada, false en caso contrario
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales del usuario no han expirado.
     * 
     * <p>En esta implementación, siempre devuelve true ya que no se implementa
     * funcionalidad de expiración de credenciales.</p>
     * 
     * @return true si las credenciales no han expirado, false en caso contrario
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta del usuario está habilitada.
     * 
     * <p>En esta implementación, siempre devuelve true ya que no se implementa
     * funcionalidad de deshabilitación de cuentas.</p>
     * 
     * @return true si la cuenta está habilitada, false en caso contrario
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
