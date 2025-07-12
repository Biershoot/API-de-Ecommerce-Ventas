package com.alejandro.ecommerceapi.entity;

/**
 * Enumeración que representa los roles de usuario en el sistema de e-commerce.
 * 
 * <p>Esta enumeración define los diferentes tipos de usuarios que pueden acceder al sistema.
 * Cada rol tiene permisos y niveles de acceso específicos dentro de la aplicación.</p>
 * 
 * <p>Roles disponibles:</p>
 * <ul>
 *   <li><strong>ADMIN</strong> - Administrador con acceso completo al sistema y capacidades de gestión</li>
 *   <li><strong>CLIENT</strong> - Cliente regular que puede navegar productos y realizar pedidos</li>
 * </ul>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
public enum Role {
    /**
     * Rol de administrador con acceso completo al sistema incluyendo gestión de usuarios,
     * gestión de productos y administración de pedidos.
     */
    ADMIN,
    
    /**
     * Rol de cliente con acceso limitado para navegar productos y realizar pedidos.
     */
    CLIENT
}

