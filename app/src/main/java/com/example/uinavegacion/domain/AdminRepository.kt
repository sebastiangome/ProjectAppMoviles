package com.example.uinavegacion.domain

/**
 * Repositorio para manejar administradores con listas en memoria
 */
class AdminRepository {
    
    // Lista de administradores predefinidos
    private val admins = mutableListOf(
        Admin("admin@steamish.com", "Administrador Principal", "Admin123!", "88776655", "SUPER_ADMIN"),
        Admin("manager@steamish.com", "Gerente de Juegos", "Manager456@", "77665544", "GAME_MANAGER"),
        Admin("support@steamish.com", "Soporte Técnico", "Support789#", "66554433", "SUPPORT"),
        Admin("moderator@steamish.com", "Moderador de Contenido", "Mod012$%", "55443322", "MODERATOR"),
        Admin("sales@steamish.com", "Administrador de Ventas", "Sales345&*", "44332211", "SALES_ADMIN")
    )
    
    /**
     * Valida las credenciales de un administrador
     */
    fun validateAdmin(email: String, password: String): Admin? {
        return admins.find { admin ->
            admin.email == email && admin.password == password
        }
    }
    
    /**
     * Registra un nuevo administrador (solo para super admin)
     */
    fun registerAdmin(email: String, name: String, password: String, phone: String, role: String): Result<Admin> {
        // Validar que el email no exista
        if (admins.any { it.email == email }) {
            return Result.failure(Exception("El email ya está registrado como administrador"))
        }
        
        // Validar formato de email
        val emailError = validateEmail(email)
        if (emailError != null) {
            return Result.failure(Exception("Email inválido: $emailError"))
        }
        
        // Validar contraseña fuerte
        val passwordError = validateStrongPassword(password)
        if (passwordError != null) {
            return Result.failure(Exception("Contraseña inválida: $passwordError"))
        }
        
        // Validar nombre (solo letras)
        val nameError = validateLettersOnly(name)
        if (nameError != null) {
            return Result.failure(Exception("Nombre inválido: $nameError"))
        }
        
        // Validar teléfono
        val phoneError = validatePhoneDigitsOnly(phone)
        if (phoneError != null) {
            return Result.failure(Exception("Teléfono inválido: $phoneError"))
        }
        
        // Crear nuevo administrador
        val newAdmin = Admin(email, name, password, phone, role)
        admins.add(newAdmin)
        
        return Result.success(newAdmin)
    }
    
    /**
     * Obtiene un administrador por email
     */
    fun getAdminByEmail(email: String): Admin? {
        return admins.find { it.email == email }
    }
    
    /**
     * Obtiene todos los administradores
     */
    fun getAllAdmins(): List<Admin> {
        return admins.toList()
    }
    
    /**
     * Verifica si un administrador tiene un rol específico
     */
    fun hasRole(email: String, role: String): Boolean {
        return admins.find { it.email == email }?.role == role
    }
    
    /**
     * Actualiza el rol de un administrador
     */
    fun updateAdminRole(email: String, newRole: String): Boolean {
        val adminIndex = admins.indexOfFirst { it.email == email }
        return if (adminIndex != -1) {
            val admin = admins[adminIndex]
            admins[adminIndex] = admin.copy(role = newRole)
            true
        } else {
            false
        }
    }
    
    /**
     * Elimina un administrador (solo para super admin)
     */
    fun removeAdmin(email: String): Boolean {
        return admins.removeIf { it.email == email }
    }
}

/**
 * Clase de datos para representar un administrador
 */
data class Admin(
    val email: String,
    val name: String,
    val password: String,
    val phone: String,
    val role: String,
    val id: String = email // Usar email como ID único
)

/**
 * Roles de administrador disponibles
 */
enum class AdminRole(val displayName: String) {
    SUPER_ADMIN("Super Administrador"),
    GAME_MANAGER("Gerente de Juegos"),
    SUPPORT("Soporte Técnico"),
    MODERATOR("Moderador de Contenido"),
    SALES_ADMIN("Administrador de Ventas")
}