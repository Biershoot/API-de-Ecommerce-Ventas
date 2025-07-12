package com.alejandro.ecommerceapi.repository;

import com.alejandro.ecommerceapi.entity.Order;
import com.alejandro.ecommerceapi.entity.OrderStatus;
import com.alejandro.ecommerceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interfaz de repositorio para operaciones de la entidad Order.
 * 
 * <p>Esta interfaz extiende {@link JpaRepository} para proporcionar operaciones CRUD básicas
 * para la entidad Order. También incluye métodos de consulta personalizados para operaciones
 * específicas de pedidos como encontrar pedidos por cliente, estado, o combinaciones de ambos.</p>
 * 
 * <p>La interfaz aprovecha Spring Data JPA para generar automáticamente
 * clases de implementación y consultas SQL basadas en nombres de métodos.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Encuentra todos los pedidos para un cliente específico.
     * 
     * <p>Este método recupera todos los pedidos que pertenecen al usuario especificado,
     * independientemente de su estado. Los pedidos típicamente se devuelven en
     * orden cronológico (más recientes primero).</p>
     * 
     * @param client El usuario cuyos pedidos se van a recuperar
     * @return Una lista de pedidos pertenecientes al cliente especificado
     */
    List<Order> findByClient(User client);
    
    /**
     * Encuentra todos los pedidos con un estado específico.
     * 
     * <p>Este método recupera todos los pedidos que tienen el estado especificado,
     * independientemente de qué cliente los haya realizado. Útil para propósitos
     * administrativos para ver todos los pedidos pendientes o cancelados.</p>
     * 
     * @param status El estado para filtrar los pedidos
     * @return Una lista de pedidos con el estado especificado
     */
    List<Order> findByStatus(OrderStatus status);
    
    /**
     * Encuentra todos los pedidos para un cliente específico con un estado específico.
     * 
     * <p>Este método combina el filtrado por cliente y estado para recuperar pedidos
     * que pertenecen a un usuario específico y tienen un estado particular. Útil
     * para que los clientes vean sus pedidos pendientes o cancelados.</p>
     * 
     * @param client El usuario cuyos pedidos se van a recuperar
     * @param status El estado para filtrar los pedidos
     * @return Una lista de pedidos pertenecientes al cliente especificado con el estado especificado
     */
    List<Order> findByClientAndStatus(User client, OrderStatus status);
}



