package com.alejandro.ecommerceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot para la API de E-commerce.
 * 
 * <p>Esta clase sirve como punto de entrada para la aplicación Spring Boot.
 * Utiliza la anotación {@code @SpringBootApplication} que combina:
 * <ul>
 *   <li>{@code @Configuration} - Marca la clase como fuente de definiciones de beans</li>
 *   <li>{@code @EnableAutoConfiguration} - Le dice a Spring Boot que comience a agregar beans basándose en la configuración del classpath</li>
 *   <li>{@code @ComponentScan} - Le dice a Spring que busque otros componentes, configuraciones y servicios</li>
 * </ul>
 * </p>
 * 
 * <p>La aplicación proporciona una API RESTful para operaciones de e-commerce incluyendo:
 * <ul>
 *   <li>Autenticación y autorización de usuarios</li>
 *   <li>Gestión de productos (operaciones CRUD)</li>
 *   <li>Procesamiento y gestión de pedidos</li>
 *   <li>Seguridad basada en JWT</li>
 * </ul>
 * </p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@SpringBootApplication
public class EcommerceApiApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     * 
     * <p>Este método inicializa el contexto de aplicación de Spring y inicia
     * el servidor web embebido. La aplicación estará disponible en el
     * puerto configurado (por defecto: 8081).</p>
     * 
     * @param args Argumentos de línea de comandos pasados a la aplicación
     */
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApiApplication.class, args);
    }

}
