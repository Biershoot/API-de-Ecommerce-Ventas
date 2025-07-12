package com.alejandro.ecommerceapi.controller;

import com.alejandro.ecommerceapi.dto.ProductRequest;
import com.alejandro.ecommerceapi.entity.Product;
import com.alejandro.ecommerceapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para operaciones relacionadas con productos.
 * 
 * <p>Este controlador proporciona endpoints para gestionar productos incluyendo:
 * <ul>
 *   <li>Operaciones CRUD completas (Crear, Leer, Actualizar, Eliminar)</li>
 *   <li>Búsqueda de productos con paginación</li>
 *   <li>Filtrado por nombre de producto</li>
 *   <li>Acceso público para lectura de productos</li>
 *   <li>Acceso restringido para operaciones de gestión (solo ADMIN)</li>
 * </ul>
 * </p>
 * 
 * <p>Los endpoints de lectura son públicos, mientras que las operaciones de
 * creación, actualización y eliminación requieren rol de administrador.</p>
 * 
 * <p>Base URL: {@code /api/products}</p>
 * 
 * @author Alejandro
 * @version 1.0
 * @since 2024
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Recupera todos los productos en el sistema.
     * 
     * <p>Este endpoint devuelve todos los productos sin paginación. Usar con precaución
     * para grandes conjuntos de datos ya que carga todos los productos en memoria.</p>
     * 
     * @return ResponseEntity con una lista de todos los productos
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    /**
     * Recupera productos con búsqueda opcional y paginación.
     * 
     * <p>Este endpoint proporciona acceso paginado a productos con funcionalidad
     * opcional de búsqueda. Los parámetros de consulta permiten:
     * <ul>
     *   <li>search: Término de búsqueda para filtrar por nombre de producto</li>
     *   <li>page: Número de página (basado en 0)</li>
     *   <li>size: Número de productos por página</li>
     * </ul>
     * </p>
     * 
     * <p>Ejemplo de uso:</p>
     * <ul>
     *   <li>{@code GET /api/products?page=0&size=10} - Primera página con 10 productos</li>
     *   <li>{@code GET /api/products?search=laptop&page=0&size=5} - Buscar "laptop" con 5 productos por página</li>
     * </ul>
     * 
     * @param search Término de búsqueda opcional para filtrar productos por nombre
     * @param page Número de página (por defecto: 0)
     * @param size Número de productos por página (por defecto: 10)
     * @return ResponseEntity con una página de productos y metadatos de paginación
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getProducts(search, page, size));
    }

    /**
     * Recupera un producto específico por su ID.
     * 
     * <p>Este endpoint devuelve un producto específico si existe, o un error 404
     * si no se encuentra el producto con el ID proporcionado.</p>
     * 
     * @param id El ID único del producto a recuperar
     * @return ResponseEntity con el producto si se encuentra, o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo producto en el sistema.
     * 
     * <p>Este endpoint permite crear un nuevo producto con la información proporcionada.
     * Solo los usuarios con rol ADMIN pueden crear productos.</p>
     * 
     * <p>Ejemplo de solicitud:</p>
     * <pre>
     * {
     *   "name": "Laptop Gaming",
     *   "description": "Laptop de alto rendimiento para gaming",
     *   "price": 1299.99,
     *   "stock": 50,
     *   "category": "Electrónicos"
     * }
     * </pre>
     * 
     * @param productRequest La solicitud que contiene los detalles del producto
     * @return ResponseEntity con el producto creado
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .category(productRequest.getCategory())
                .build();
        return ResponseEntity.ok(productService.save(product));
    }

    /**
     * Actualiza un producto existente por su ID.
     * 
     * <p>Este endpoint permite actualizar la información de un producto existente.
     * Solo los usuarios con rol ADMIN pueden actualizar productos. Si el producto
     * no existe, se devuelve un error 404.</p>
     * 
     * <p>Ejemplo de solicitud:</p>
     * <pre>
     * {
     *   "name": "Laptop Gaming Pro",
     *   "description": "Laptop de alto rendimiento actualizada",
     *   "price": 1399.99,
     *   "stock": 45,
     *   "category": "Electrónicos"
     * }
     * </pre>
     * 
     * @param id El ID del producto a actualizar
     * @param productRequest La solicitud con los nuevos datos del producto
     * @return ResponseEntity con el producto actualizado
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product updatedProduct = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .category(productRequest.getCategory())
                .build();
        return ResponseEntity.ok(productService.update(id, updatedProduct));
    }

    /**
     * Elimina un producto por su ID.
     * 
     * <p>Este endpoint elimina permanentemente un producto del sistema.
     * Solo los usuarios con rol ADMIN pueden eliminar productos. Si el producto
     * no existe, la operación se ignora silenciosamente.</p>
     * 
     * @param id El ID del producto a eliminar
     * @return ResponseEntity sin contenido (204 No Content)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

