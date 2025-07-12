# API de E-commerce

Una API RESTful completa para operaciones de e-commerce construida con Spring Boot, que incluye autenticación de usuarios, gestión de productos y procesamiento de pedidos con seguridad basada en JWT.

## 🚀 Características

### Autenticación y Autorización
- **Autenticación basada en JWT** con expiración de 24 horas
- **Control de acceso basado en roles** (ADMIN y CLIENT)
- **Encriptación segura de contraseñas** usando BCrypt
- **Endpoints de registro y login** de usuarios

### Gestión de Productos
- **Operaciones CRUD** para productos
- **Funcionalidad de búsqueda** con coincidencia insensible a mayúsculas
- **Soporte de paginación** para recuperación eficiente de datos
- **Gestión de inventario** con actualizaciones automáticas durante los pedidos

### Gestión de Pedidos
- **Creación de pedidos** con múltiples productos y cantidades
- **Seguimiento del estado del pedido** (PENDING, CANCELLED)
- **Cancelación de pedidos** por parte de clientes (solo para pedidos PENDING)
- **Filtrado de pedidos** por estado y cliente
- **Reducción automática de inventario** cuando se realizan pedidos

### Características de Seguridad
- **Validación de tokens JWT** en endpoints protegidos
- **Protección de endpoints basada en roles**
- **Filtrado de solicitudes** para autenticación
- **Configuración segura** con configuraciones CORS apropiadas

## 🛠️ Stack Tecnológico

- **Java 17**
- **Spring Boot 3.5.x**
- **Spring Security** con JWT
- **Spring Data JPA**
- **Base de datos H2** (en memoria para desarrollo)
- **Lombok** para reducir código repetitivo
- **Maven** para gestión de dependencias

## 📋 Prerrequisitos

- Java 17 o superior
- Maven 3.6 o superior
- Cualquier IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Comenzando

### 1. Clonar el Repositorio
```bash
git clone <url-del-repositorio>
cd ecommerce-api
```

### 2. Compilar el Proyecto
```bash
mvn clean compile
```

### 3. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

La aplicación se iniciará en el puerto **8081** (http://localhost:8081)

## 📚 Documentación de la API

### URL Base
```
http://localhost:8081
```

### Endpoints de Autenticación

#### Registrar Usuario
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "Juan Pérez",
  "email": "juan@ejemplo.com",
  "password": "contraseña123",
  "role": "CLIENT"
}
```

#### Iniciar Sesión
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "juan@ejemplo.com",
  "password": "contraseña123"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Endpoints de Productos

#### Obtener Todos los Productos (con paginación)
```http
GET /api/products?page=0&size=10&search=laptop
Authorization: Bearer <jwt-token>
```

#### Obtener Producto por ID
```http
GET /api/products/{id}
Authorization: Bearer <jwt-token>
```

#### Crear Producto (solo ADMIN)
```http
POST /api/products
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "name": "Laptop",
  "description": "Laptop de alto rendimiento",
  "price": 999.99,
  "stock": 50,
  "category": "Electrónicos"
}
```

#### Actualizar Producto (solo ADMIN)
```http
PUT /api/products/{id}
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "name": "Laptop Actualizada",
  "description": "Descripción actualizada",
  "price": 899.99,
  "stock": 45,
  "category": "Electrónicos"
}
```

#### Eliminar Producto (solo ADMIN)
```http
DELETE /api/products/{id}
Authorization: Bearer <jwt-token>
```

### Endpoints de Pedidos

#### Crear Pedido
```http
POST /api/orders
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

#### Obtener Pedidos del Cliente
```http
GET /api/orders
Authorization: Bearer <jwt-token>
```

#### Obtener Todos los Pedidos (solo ADMIN)
```http
GET /api/orders/all
Authorization: Bearer <jwt-token>
```

#### Obtener Pedidos por Estado (solo ADMIN)
```http
GET /api/orders/all?status=PENDING
Authorization: Bearer <jwt-token>
```

#### Cancelar Pedido
```http
PUT /api/orders/{orderId}/cancel
Authorization: Bearer <jwt-token>
```

## 🏗️ Estructura del Proyecto

```
src/main/java/com/alejandro/ecommerceapi/
├── config/                 # Clases de configuración
│   └── SecurityConfig.java # Configuración de Spring Security
├── controller/             # Controladores REST
│   ├── AuthController.java
│   ├── OrderController.java
│   └── ProductController.java
├── dto/                    # Objetos de Transferencia de Datos
│   ├── AuthResponse.java
│   ├── CreateOrderRequest.java
│   ├── LoginRequest.java
│   ├── OrderItemRequest.java
│   ├── ProductRequest.java
│   └── RegisterRequest.java
├── entity/                 # Entidades JPA
│   ├── Order.java
│   ├── OrderDetail.java
│   ├── OrderStatus.java
│   ├── Product.java
│   ├── Role.java
│   └── User.java
├── repository/             # Capa de acceso a datos
│   ├── OrderDetailRepository.java
│   ├── OrderRepository.java
│   ├── ProductRepository.java
│   └── UserRepository.java
├── security/               # Componentes de seguridad
│   ├── CustomUserDetails.java
│   ├── JwtFilter.java
│   └── JwtService.java
├── service/                # Capa de lógica de negocio
│   ├── AuthService.java
│   ├── OrderService.java
│   └── ProductService.java
└── EcommerceApiApplication.java # Clase principal de la aplicación
```

## 🔐 Configuración de Seguridad

### Formato del Token JWT
- **Algoritmo**: HS256
- **Expiración**: 24 horas
- **Header**: `Authorization: Bearer <token>`

### Control de Acceso Basado en Roles
- **ADMIN**: Acceso completo a todos los endpoints
- **CLIENT**: Acceso limitado (sus propios pedidos, navegación de productos)

### Endpoints Protegidos
- Todos los endpoints excepto `/api/auth/*` requieren autenticación
- Los endpoints de gestión de productos requieren rol ADMIN
- La gestión de pedidos tiene restricciones específicas por rol

## 🗄️ Esquema de Base de Datos

### Tabla Users
- `id` (Clave Primaria)
- `name` (VARCHAR)
- `email` (VARCHAR, Único)
- `password` (VARCHAR, Encriptado)
- `role` (ENUM: ADMIN, CLIENT)

### Tabla Products
- `id` (Clave Primaria)
- `name` (VARCHAR)
- `description` (TEXT)
- `price` (DECIMAL)
- `stock` (INTEGER)
- `category` (VARCHAR)

### Tabla Orders
- `id` (Clave Primaria)
- `created_at` (TIMESTAMP)
- `total` (DECIMAL)
- `user_id` (Clave Foránea)
- `status` (ENUM: PENDING, CANCELLED)

### Tabla Order Details
- `id` (Clave Primaria)
- `quantity` (INTEGER)
- `subtotal` (DECIMAL)
- `order_id` (Clave Foránea)
- `product_id` (Clave Foránea)

## 🧪 Pruebas

### Usando Postman

1. **Registrar un nuevo usuario:**
   - POST `http://localhost:8081/api/auth/register`
   - Body: Datos de registro del usuario

2. **Iniciar sesión para obtener token JWT:**
   - POST `http://localhost:8081/api/auth/login`
   - Body: Credenciales de login

3. **Usar el token para solicitudes autenticadas:**
   - Agregar header: `Authorization: Bearer <tu-jwt-token>`

### Datos de Prueba de Ejemplo

#### Crear Usuario Administrador
```json
{
  "name": "Usuario Administrador",
  "email": "admin@ejemplo.com",
  "password": "admin123",
  "role": "ADMIN"
}
```

#### Crear Usuario Cliente
```json
{
  "name": "Usuario Cliente",
  "email": "cliente@ejemplo.com",
  "password": "cliente123",
  "role": "CLIENT"
}
```

#### Crear Producto
```json
{
  "name": "Laptop Gaming",
  "description": "Laptop gaming de alto rendimiento con gráficos RTX",
  "price": 1499.99,
  "stock": 25,
  "category": "Electrónicos"
}
```

## 🔧 Configuración

### Propiedades de la Aplicación
```properties
# Configuración del Servidor
server.port=8081

# Configuración de Base de Datos (H2 en memoria)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Configuración JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Consola H2 (para desarrollo)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## 🚀 Despliegue

### Desarrollo Local
```bash
mvn spring-boot:run
```

### Compilación para Producción
```bash
mvn clean package
java -jar target/ecommerce-api-0.0.1-SNAPSHOT.jar
```

## 📝 Ejemplos de Respuestas de la API

### Respuesta Exitosa
```json
{
  "id": 1,
  "name": "Nombre del Producto",
  "description": "Descripción del producto",
  "price": 99.99,
  "stock": 50,
  "category": "Electrónicos"
}
```

### Respuesta de Error
```json
{
  "timestamp": "2024-01-01T12:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Producto no encontrado",
  "path": "/api/products/999"
}
```

## 🤝 Contribuir

1. Haz fork del repositorio
2. Crea una rama de características (`git checkout -b feature/caracteristica-increible`)
3. Commit de tus cambios (`git commit -m 'Agregar alguna característica increíble'`)
4. Push a la rama (`git push origin feature/caracteristica-increible`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

## 👨‍💻 Autor

**Alejandro**
- Email: [tu-email@ejemplo.com]
- LinkedIn: [tu-perfil-linkedin]

## 🙏 Agradecimientos

- Equipo de Spring Boot por el excelente framework
- Biblioteca JJWT para funcionalidad JWT
- Lombok por reducir código repetitivo
- Base de datos H2 por soporte de base de datos en memoria

---

**Nota**: Esta es una versión de desarrollo. Para uso en producción, considera:
- Usar una base de datos de producción (PostgreSQL, MySQL)
- Externalizar configuración sensible
- Implementar logging apropiado
- Agregar pruebas unitarias e integración completas
- Configurar pipelines CI/CD 