# 🔐 Credenciales de Administradores - Sistema Steamish

## 📋 Lista de Administradores Disponibles

### 🎯 **Administrador Principal**
- **Email:** `admin@steamish.com`
- **Contraseña:** `Admin123!`
- **Rol:** Super Administrador
- **Teléfono:** 88776655

### 🎮 **Gerente de Juegos**
- **Email:** `manager@steamish.com`
- **Contraseña:** `Manager456@`
- **Rol:** Gerente de Juegos
- **Teléfono:** 77665544

### 🛠️ **Soporte Técnico**
- **Email:** `support@steamish.com`
- **Contraseña:** `Support789#`
- **Rol:** Soporte Técnico
- **Teléfono:** 66554433

### 👮 **Moderador de Contenido**
- **Email:** `moderator@steamish.com`
- **Contraseña:** `Mod012$%`
- **Rol:** Moderador de Contenido
- **Teléfono:** 55443322

### 💰 **Administrador de Ventas**
- **Email:** `sales@steamish.com`
- **Contraseña:** `Sales345&*`
- **Rol:** Administrador de Ventas
- **Teléfono:** 44332211

---

## ✅ **Parámetros de Validación Cumplidos**

Todas las credenciales cumplen con los siguientes requisitos:

### 📧 **Email:**
- Formato válido de correo electrónico
- Dominio corporativo (@steamish.com)

### 🔒 **Contraseña:**
- Mínimo 8 caracteres
- Al menos una letra mayúscula
- Al menos una letra minúscula  
- Al menos un número
- Al menos un símbolo especial
- Sin espacios en blanco

### 📱 **Teléfono:**
- Entre 7 y 8 dígitos
- Solo números (sin caracteres especiales)

### 👤 **Nombre:**
- Solo letras y espacios
- Sin números ni caracteres especiales

---

## 🏗️ **Arquitectura del Sistema**

### **AdminRepository.kt**
- Maneja la lista de administradores en memoria
- Validación de credenciales de admin
- Operaciones CRUD para administradores
- Gestión de roles y permisos

### **Funciones Principales:**
- `validateAdmin()` - Valida credenciales
- `registerAdmin()` - Registra nuevo admin (solo super admin)
- `getAdminByEmail()` - Busca admin por email
- `hasRole()` - Verifica permisos de rol
- `updateAdminRole()` - Actualiza rol de admin

### **Roles Disponibles:**
- `SUPER_ADMIN` - Acceso completo al sistema
- `GAME_MANAGER` - Gestión de catálogo de juegos
- `SUPPORT` - Soporte técnico a usuarios
- `MODERATOR` - Moderación de contenido
- `SALES_ADMIN` - Administración de ventas

---

## 🚀 **Uso en la Aplicación**

El sistema ahora valida automáticamente si las credenciales ingresadas corresponden a:
1. **Administrador** (usando AdminRepository)
2. **Usuario normal** (usando UserRepository)

La validación de administradores tiene prioridad sobre la validación de usuarios normales.

---

*Creado el 8 de octubre de 2025*