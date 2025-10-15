# 🗄️ Estructura de Base de Datos - UINavegacion

## 📊 Diagrama de Entidades

```
UserEntity (users)
    ├── LicenciaEntity (licencias) [1:N]
    ├── OrdenCompraEntity (ordenes_compra) [1:N]
    └── ReservaEntity (reservas) [1:N]

CategoriaEntity (categorias)
    └── JuegoEntity (juegos) [1:N]

GeneroEntity (generos)
    └── JuegoEntity (juegos) [1:N]

JuegoEntity (juegos)
    ├── LicenciaEntity (licencias) [1:N]
    ├── DetalleEntity (detalles_orden) [1:N]
    └── ReservaEntity (reservas) [1:N]

EstadoEntity (estados)
    ├── OrdenCompraEntity (ordenes_compra) [1:N]
    └── ReservaEntity (reservas) [1:N]

RolEntity (roles)
    └── [Relación futura con UserEntity]

OrdenCompraEntity (ordenes_compra)
    └── DetalleEntity (detalles_orden) [1:N]
```

---

## 📋 Tablas Creadas

### 1. **users** (UserEntity)
Usuario del sistema

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | PK, autoincremental |
| name | String | Nombre del usuario |
| email | String | Correo electrónico |
| phone | String | Teléfono |
| password | String | Contraseña |

**Operaciones DAO:**
- `insert()` - Insertar usuario
- `getByEmail()` - Buscar por email
- `getById()` - Buscar por id
- `getAll()` - Obtener todos
- `count()` - Contar usuarios
- `getAllOrderedById()` - Listar ordenados
- `update()` - Actualizar usuario
- `delete()` - Eliminar usuario

---

### 2. **categorias** (CategoriaEntity)
Categorías de juegos (Ej: Acción, RPG, Estrategia)

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | PK, autoincremental |
| nombre | String | Nombre de la categoría |
| descripcion | String | Descripción |

**Operaciones DAO:**
- `insert()`, `getById()`, `getByNombre()`, `getAll()`, `count()`, `getAllOrderedByNombre()`, `update()`, `delete()`

---

### 3. **generos** (GeneroEntity)
Géneros de juegos (Ej: Aventura, Terror, Simulación)

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | PK, autoincremental |
| nombre | String | Nombre del género |
| descripcion | String | Descripción |

**Operaciones DAO:**
- `insert()`, `getById()`, `getByNombre()`, `getAll()`, `count()`, `getAllOrderedByNombre()`, `update()`, `delete()`

---

### 4. **juegos** (JuegoEntity)
Catálogo de juegos disponibles

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | PK, autoincremental |
| nombre | String | Nombre del juego |
| descripcion | String | Descripción |
| precio | Double | Precio |
| stock | Int | Stock disponible |
| imagenUrl | String? | URL de imagen |
| desarrollador | String | Desarrollador |
| fechaLanzamiento | String | Fecha de lanzamiento |
| categoriaId | Long | FK a categorias |
| generoId | Long | FK a generos |

**Operaciones DAO:**
- `insert()`, `getById()`, `getByNombre()`, `getAll()`
- `getByCategoriaId()` - Juegos por categoría
- `getByGeneroId()` - Juegos por género
- `getJuegosDisponibles()` - Solo con stock > 0
- `getAllOrderedByNombre()`, `getAllOrderedByPrecio()`
- `updateStock()` - Actualizar solo stock
- `update()`, `delete()`

---

### 5. **estados** (EstadoEntity)
Estados para órdenes y reservas (Ej: Pendiente, Completado, Cancelado)

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | PK, autoincremental |
| nombre | String | Nombre del estado |
| descripcion | String | Descripción |

**Operaciones DAO:**
- `insert()`, `getById()`, `getByNombre()`, `getAll()`, `count()`, `getAllOrderedByNombre()`, `update()`, `delete()`

---

### 6. **roles** (RolEntity)
Roles de usuario (Ej: Usuario, Administrador, Moderador)

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | PK, autoincremental |
| nombre | String | Nombre del rol |
| descripcion | String | Descripción |

**Operaciones DAO:**
- `insert()`, `getById()`, `getByNombre()`, `getAll()`, `count()`, `getAllOrderedByNombre()`, `update()`, `delete()`

---

### 7. **licencias** (LicenciaEntity)
Licencias de juegos adquiridas por usuarios

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | PK, autoincremental |
| userId | Long | FK a users |
| juegoId | Long | FK a juegos |
| codigoLicencia | String | Código único |
| fechaCompra | String | Fecha de compra |
| fechaExpiracion | String? | Fecha de expiración |
| activa | Boolean | Estado activo |

**Operaciones DAO:**
- `insert()`, `getById()`, `getByCodigo()`
- `getByUserId()` - Licencias de un usuario
- `getActivasByUserId()` - Solo activas
- `getByJuegoId()` - Licencias de un juego
- `hasLicencia()` - Verificar si usuario tiene licencia
- `desactivar()` - Desactivar licencia
- `update()`, `delete()`

---

### 8. **ordenes_compra** (OrdenCompraEntity)
Órdenes de compra realizadas

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | PK, autoincremental |
| userId | Long | FK a users |
| fechaOrden | String | Fecha de orden |
| total | Double | Total de la orden |
| estadoId | Long | FK a estados |
| metodoPago | String | Método de pago |
| direccionEnvio | String? | Dirección de envío |

**Operaciones DAO:**
- `insert()`, `getById()`, `getAll()`
- `getByUserId()` - Órdenes de un usuario
- `getByEstadoId()` - Órdenes por estado
- `getByUserIdOrderedByFecha()` - Ordenadas por fecha
- `getTotalVentas()` - Total de ventas
- `updateEstado()` - Actualizar solo estado
- `update()`, `delete()`

---

### 9. **detalles_orden** (DetalleEntity)
Detalles (ítems) de cada orden de compra

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | PK, autoincremental |
| ordenId | Long | FK a ordenes_compra |
| juegoId | Long | FK a juegos |
| cantidad | Int | Cantidad |
| precioUnitario | Double | Precio unitario |
| subtotal | Double | Subtotal |

**Operaciones DAO:**
- `insert()`, `getById()`, `getAll()`
- `getByOrdenId()` - Detalles de una orden
- `getByJuegoId()` - Detalles de un juego
- `getTotalOrden()` - Calcular total
- `deleteByOrdenId()` - Eliminar todos de una orden
- `update()`, `delete()`

---

### 10. **reservas** (ReservaEntity)
Reservas de juegos por usuarios

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | PK, autoincremental |
| userId | Long | FK a users |
| juegoId | Long | FK a juegos |
| fechaReserva | String | Fecha de reserva |
| fechaVencimiento | String | Fecha de vencimiento |
| estadoId | Long | FK a estados |
| cantidad | Int | Cantidad reservada |

**Operaciones DAO:**
- `insert()`, `getById()`, `getAll()`
- `getByUserId()` - Reservas de un usuario
- `getByJuegoId()` - Reservas de un juego
- `getByEstadoId()` - Reservas por estado
- `getActivasByUserId()` - Reservas activas
- `hasReservaActiva()` - Verificar reserva activa
- `updateEstado()` - Actualizar solo estado
- `update()`, `delete()`

---

## 🔧 Configuración de Room

### AppDatabase.kt
Base de datos principal con:
- **10 entidades** registradas
- **10 DAOs** expuestos
- Versión: 1
- Nombre archivo: `ui_navegacion.db`
- Seed data inicial para usuarios

### Dependencias Gradle
```kotlin
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")
```

---

## 🚀 Uso en la Aplicación

### Ejemplo de uso:
```kotlin
// Obtener instancia de la BD
val db = AppDatabase.getInstance(context)

// Usar DAOs
val users = db.userDao().getAll()
val juegos = db.juegoDao().getJuegosDisponibles()
val ordenesUsuario = db.ordenCompraDao().getByUserId(userId)
```

---

## 📝 Notas Importantes

1. **Foreign Keys**: Todas las relaciones tienen `onDelete = CASCADE`
2. **Índices**: Creados automáticamente en columnas FK para optimizar consultas
3. **Suspend functions**: Todas las operaciones son asíncronas (uso con coroutines)
4. **Seed data**: Usuarios iniciales se cargan al crear la BD por primera vez

---

*Generado el 14 de octubre de 2025*
