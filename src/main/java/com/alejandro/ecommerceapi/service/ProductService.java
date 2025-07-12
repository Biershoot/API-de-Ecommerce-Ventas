package com.alejandro.ecommerceapi.service;

import com.alejandro.ecommerceapi.entity.Product;
import com.alejandro.ecommerceapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Clase de servicio para manejar operaciones de negocio relacionadas con productos.
 * 
 * <p>Este servicio proporciona métodos para gestionar productos incluyendo operaciones CRUD,
 * funcionalidad de búsqueda y soporte de paginación. Actúa como intermediario entre
 * los controladores y la capa de repositorio, implementando lógica de negocio para
 * la gestión de productos.</p>
 * 
 * <p>El servicio se integra con {@link ProductRepository} para persistencia de datos
 * y proporciona una API limpia para operaciones de productos.</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Recupera todos los productos en el sistema.
     * 
     * <p>Este método devuelve todos los productos sin ningún filtrado o paginación.
     * Usar con precaución para grandes conjuntos de datos ya que carga todos los productos en memoria.</p>
     * 
     * @return Una lista de todos los productos en el sistema
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Encuentra un producto por su identificador único.
     * 
     * @param id El identificador único del producto a encontrar
     * @return Un Optional que contiene el producto si se encuentra, o vacío si no se encuentra
     */
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Guarda un nuevo producto en el sistema.
     * 
     * <p>Este método crea un nuevo registro de producto en la base de datos. Si el producto
     * tiene un ID, será ignorado y se generará un nuevo ID.</p>
     * 
     * @param product El producto a guardar
     * @return El producto guardado con ID generado
     */
    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * Actualiza un producto existente por su ID.
     * 
     * <p>Este método actualiza las propiedades del producto con los valores del
     * parámetro updatedProduct. Solo se actualizan los campos nombre, descripción, precio y stock.</p>
     * 
     * @param id El ID del producto a actualizar
     * @param updatedProduct El producto con valores actualizados
     * @return El producto actualizado
     * @throws RuntimeException si el producto con el ID dado no se encuentra
     */
    public Product update(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setStock(updatedProduct.getStock());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    /**
     * Elimina un producto por su ID.
     * 
     * <p>Este método elimina el producto de la base de datos. Si el producto
     * no existe, la operación se ignora silenciosamente.</p>
     * 
     * @param id El ID del producto a eliminar
     */
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Recupera productos con búsqueda opcional y paginación.
     * 
     * <p>Este método proporciona acceso paginado a productos con funcionalidad
     * opcional de búsqueda basada en nombres. Si se proporciona un término de búsqueda,
     * realiza una búsqueda insensible a mayúsculas en los nombres de productos.</p>
     * 
     * @param search Término de búsqueda opcional para filtrar productos por nombre
     * @param page El número de página (basado en 0)
     * @param size El número de productos por página
     * @return Una Page que contiene los productos coincidentes con metadatos de paginación
     */
    public Page<Product> getProducts(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (search != null && !search.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(search, pageable);
        }
        return productRepository.findAll(pageable);
    }
}

