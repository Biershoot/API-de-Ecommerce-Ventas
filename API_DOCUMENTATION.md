# Documentación de la API

## Resumen

Este documento proporciona documentación completa para los endpoints de la API de E-commerce, incluyendo formatos de solicitud/respuesta, requisitos de autenticación y manejo de errores.

## URL Base
```
http://localhost:8081
```

## Autenticación

Todos los endpoints excepto los endpoints de autenticación requieren un token JWT en el header de Authorization:
```
Authorization: Bearer <tu-jwt-token>
```

## Endpoints

### Autenticación

#### 1. Registrar Usuario

**Endpoint:** `POST /api/auth/register`

**Descripción:** Crea una nueva cuenta de usuario y devuelve un token JWT.

**Cuerpo de la Solicitud:**
```json
{
  "name": "Juan Pérez",
  "email": "juan@ejemplo.com",
  "password": "contraseña123",
  "role": "CLIENT"
}
```

**Respuesta (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huQGV4YW1wbGUuY29tIiwiaWF0IjoxNjM5NzI5NjAwLCJleHAiOjE2Mzk4MTYwMDB9.signature"
}
```

**Respuesta de Error (400 Bad Request):**
```json
{
  "timestamp": "2024-01-01T12:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "El email ya existe",
  "path": "/api/auth/register"
}
```

---

#### 2. Iniciar Sesión

**Endpoint:** `POST /api/auth/login`

**Descripción:** Autentica las credenciales del usuario y devuelve un token JWT.

**Cuerpo de la Solicitud:**
```json
{
  "email": "juan@ejemplo.com",
  "password": "contraseña123"
}
```

**Respuesta (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huQGV4YW1wbGUuY29tIiwiaWF0IjoxNjM5NzI5NjAwLCJleHAiOjE2Mzk4MTYwMDB9.signature"
}
```

**Respuesta de Error (401 Unauthorized):**
```json
{
  "timestamp": "2024-01-01T12:00:00.000+00:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Credenciales inválidas",
  "path": "/api/auth/login"
}
```

---

### Productos

#### 3. Obtener Todos los Productos (con paginación y búsqueda)

**Endpoint:** `GET /api/products`

**Descripción:** Recupera productos con funcionalidad opcional de paginación y búsqueda.

**Parámetros de Consulta:**
- `page` (opcional): Número de página (basado en 0, por defecto: 0)
- `size` (opcional): Número de elementos por página (por defecto: 10)
- `search` (opcional): Término de búsqueda para nombres de productos

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Ejemplo de Solicitud:**
```
GET /api/products?page=0&size=5&search=laptop
```

**Respuesta (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Laptop Gaming",
      "description": "Laptop gaming de alto rendimiento",
      "price": 1499.99,
      "stock": 25,
      "category": "Electrónicos"
    },
    {
      "id": 2,
      "name": "Laptop de Negocios",
      "description": "Laptop profesional para negocios",
      "price": 999.99,
      "stock": 15,
      "category": "Electrónicos"
    }
  ],
  "pageable": {
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 5,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 2,
  "totalPages": 1,
  "last": true,
  "size": 5,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 2,
  "first": true,
  "empty": false
}
```

---

#### 4. Obtener Producto por ID

**Endpoint:** `GET /api/products/{id}`

**Descripción:** Recupera un producto específico por su ID.

**Parámetros de Ruta:**
- `id`: ID del producto

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Ejemplo de Solicitud:**
```
GET /api/products/1
```

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "name": "Laptop Gaming",
  "description": "Laptop gaming de alto rendimiento con gráficos RTX",
  "price": 1499.99,
  "stock": 25,
  "category": "Electrónicos"
}
```

**Respuesta de Error (404 Not Found):**
```json
{
  "timestamp": "2024-01-01T12:00:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Producto no encontrado",
  "path": "/api/products/999"
}
```

---

#### 5. Crear Producto (solo ADMIN)

**Endpoint:** `POST /api/products`

**Descripción:** Crea un nuevo producto. Requiere rol ADMIN.

**Headers:**
```
Authorization: Bearer <jwt-token>
Content-Type: application/json
```

**Cuerpo de la Solicitud:**
```json
{
  "name": "Nuevo Producto",
  "description": "Descripción del producto",
  "price": 99.99,
  "stock": 50,
  "category": "Electrónicos"
}
```

**Respuesta (201 Created):**
```json
{
  "id": 3,
  "name": "Nuevo Producto",
  "description": "Descripción del producto",
  "price": 99.99,
  "stock": 50,
  "category": "Electrónicos"
}
```

**Respuesta de Error (403 Forbidden):**
```json
{
  "timestamp": "2024-01-01T12:00:00.000+00:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Acceso denegado",
  "path": "/api/products"
}
```

---

#### 6. Actualizar Producto (solo ADMIN)

**Endpoint:** `PUT /api/products/{id}`

**Descripción:** Actualiza un producto existente. Requiere rol ADMIN.

**Parámetros de Ruta:**
- `id`: ID del producto

**Headers:**
```
Authorization: Bearer <jwt-token>
Content-Type: application/json
```

**Cuerpo de la Solicitud:**
```json
{
  "name": "Producto Actualizado",
  "description": "Descripción actualizada",
  "price": 89.99,
  "stock": 45,
  "category": "Electrónicos"
}
```

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "name": "Producto Actualizado",
  "description": "Descripción actualizada",
  "price": 89.99,
  "stock": 45,
  "category": "Electrónicos"
}
```

**Respuesta de Error (404 Not Found):**
```json
{
  "timestamp": "2024-01-01T12:00:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Producto no encontrado",
  "path": "/api/products/999"
}
```

---

#### 7. Eliminar Producto (solo ADMIN)

**Endpoint:** `DELETE /api/products/{id}`

**Descripción:** Elimina un producto. Requiere rol ADMIN.

**Parámetros de Ruta:**
- `id`: ID del producto

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Respuesta (204 No Content):**
```
(Sin cuerpo de respuesta)
```

**Respuesta de Error (404 Not Found):**
```json
{
  "timestamp": "2024-01-01T12:00:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Producto no encontrado",
  "path": "/api/products/999"
}
```

---

### Pedidos

#### 8. Crear Pedido

**Endpoint:** `POST /api/orders`

**Descripción:** Crea un nuevo pedido con múltiples productos.

**Headers:**
```
Authorization: Bearer <jwt-token>
Content-Type: application/json
```

**Cuerpo de la Solicitud:**
```json
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

**Respuesta (201 Created):**
```json
{
  "id": 1,
  "createdAt": "2024-01-01T12:00:00",
  "total": 3998.97,
  "client": {
    "id": 1,
    "name": "Juan Pérez",
    "email": "juan@ejemplo.com",
    "role": "CLIENT"
  },
  "details": [
    {
      "id": 1,
      "quantity": 2,
      "subtotal": 2999.98,
      "product": {
        "id": 1,
        "name": "Laptop Gaming",
        "description": "Laptop gaming de alto rendimiento",
        "price": 1499.99,
        "stock": 23,
        "category": "Electrónicos"
      }
    },
    {
      "id": 2,
      "quantity": 1,
      "subtotal": 999.99,
      "product": {
        "id": 2,
        "name": "Laptop de Negocios",
        "description": "Laptop profesional para negocios",
        "price": 999.99,
        "stock": 14,
        "category": "Electrónicos"
      }
    }
  ],
  "status": "PENDING"
}
```

**Respuesta de Error (400 Bad Request):**
```json
{
  "timestamp": "2024-01-01T12:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "No hay suficiente stock para el producto: Laptop Gaming",
  "path": "/api/orders"
}
```

---

#### 9. Obtener Pedidos del Cliente

**Endpoint:** `GET /api/orders`

**Descripción:** Recupera todos los pedidos del usuario autenticado.

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "createdAt": "2024-01-01T12:00:00",
    "total": 3998.97,
    "client": {
      "id": 1,
      "name": "Juan Pérez",
      "email": "juan@ejemplo.com",
      "role": "CLIENT"
    },
    "details": [
      {
        "id": 1,
        "quantity": 2,
        "subtotal": 2999.98,
        "product": {
          "id": 1,
          "name": "Laptop Gaming",
          "description": "Laptop gaming de alto rendimiento",
          "price": 1499.99,
          "stock": 23,
          "category": "Electrónicos"
        }
      }
    ],
    "status": "PENDING"
  }
]
```

---

#### 10. Obtener Todos los Pedidos (solo ADMIN)

**Endpoint:** `GET /api/orders/all`

**Descripción:** Recupera todos los pedidos en el sistema. Requiere rol ADMIN.

**Parámetros de Consulta:**
- `status` (opcional): Filtrar por estado del pedido (PENDING, CANCELLED)

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Ejemplo de Solicitud:**
```
GET /api/orders/all?status=PENDING
```

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "createdAt": "2024-01-01T12:00:00",
    "total": 3998.97,
    "client": {
      "id": 1,
      "name": "Juan Pérez",
      "email": "juan@ejemplo.com",
      "role": "CLIENT"
    },
    "details": [
      {
        "id": 1,
        "quantity": 2,
        "subtotal": 2999.98,
        "product": {
          "id": 1,
          "name": "Laptop Gaming",
          "description": "Laptop gaming de alto rendimiento",
          "price": 1499.99,
          "stock": 23,
          "category": "Electrónicos"
        }
      }
    ],
    "status": "PENDING"
  }
]
```

---

#### 11. Cancelar Pedido

**Endpoint:** `PUT /api/orders/{orderId}/cancel`

**Descripción:** Cancela un pedido. Solo el propietario del pedido puede cancelar sus propios pedidos, y solo los pedidos PENDING pueden ser cancelados.

**Parámetros de Ruta:**
- `orderId`: ID del pedido

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "createdAt": "2024-01-01T12:00:00",
  "total": 3998.97,
  "client": {
    "id": 1,
    "name": "Juan Pérez",
    "email": "juan@ejemplo.com",
    "role": "CLIENT"
  },
  "details": [
    {
      "id": 1,
      "quantity": 2,
      "subtotal": 2999.98,
      "product": {
        "id": 1,
        "name": "Laptop Gaming",
        "description": "Laptop gaming de alto rendimiento",
        "price": 1499.99,
        "stock": 23,
        "category": "Electrónicos"
      }
    }
  ],
  "status": "CANCELLED"
}
```

**Respuesta de Error (400 Bad Request):**
```json
{
  "timestamp": "2024-01-01T12:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Solo se pueden cancelar pedidos con estado PENDING",
  "path": "/api/orders/1/cancel"
}
```

**Respuesta de Error (403 Forbidden):**
```json
{
  "timestamp": "2024-01-01T12:00:00.000+00:00",
  "status": 403,
  "error": "Forbidden",
  "message": "No puedes cancelar este pedido",
  "path": "/api/orders/1/cancel"
}
```

---

## Códigos de Error

| Código de Estado | Descripción |
|------------------|-------------|
| 200 | OK - Solicitud exitosa |
| 201 | Created - Recurso creado exitosamente |
| 204 | No Content - Solicitud exitosa, sin cuerpo de respuesta |
| 400 | Bad Request - Datos de solicitud inválidos |
| 401 | Unauthorized - Autenticación requerida o fallida |
| 403 | Forbidden - Acceso denegado (permisos insuficientes) |
| 404 | Not Found - Recurso no encontrado |
| 500 | Internal Server Error - Error del servidor |

## Tipos de Datos

### Rol de Usuario
```json
"ADMIN" | "CLIENT"
```

### Estado del Pedido
```json
"PENDING" | "CANCELLED"
```

### Formato de Timestamp
Todos los timestamps están en formato ISO 8601:
```
YYYY-MM-DDTHH:mm:ss
```

## Limitación de Tasa

Actualmente, no hay límites de tasa implementados. Para uso en producción, considera implementar limitación de tasa para prevenir abuso.

## Versionado

Esta es la versión 1.0 de la API. Las versiones futuras se indicarán en la ruta de la URL (ej., `/api/v2/`).

## Soporte

Para soporte de la API o preguntas, por favor contacta al equipo de desarrollo o crea un issue en el repositorio del proyecto. 