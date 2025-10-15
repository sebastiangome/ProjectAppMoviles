package com.example.uinavegacion.domain

/**
 * Repositorio simple para manejar usuarios con listas en memoria
 */
class UserRepository {
    
    // Lista de usuarios registrados
    private val users = mutableListOf(
        User("user1@demo.com", "Usuario1", "Password123!", "12345678"),
        User("user2@demo.com", "Usuario2", "Password123!", "87654321"),
        User("demo@demo.com", "Usuario Demo", "Password123!", "11223344"),
        User("test@test.com", "Usuario Test", "Password123!", "99887766")
    )
    
    /**
     * Valida las credenciales de un usuario
     */
    fun validateUser(email: String, password: String): User? {
        return users.find { user ->
            user.email == email && user.password == password
        }
    }
    
    /**
     * Registra un nuevo usuario
     */
    fun registerUser(email: String, name: String, password: String, phone: String): Result<User> {
        // Validar que el email no exista
        if (users.any { it.email == email }) {
            return Result.failure(Exception("El email ya está registrado"))
        }
        
        // Crear nuevo usuario
        val newUser = User(email, name, password, phone)
        users.add(newUser)
        
        return Result.success(newUser)
    }
    
    /**
     * Obtiene un usuario por email
     */
    fun getUserByEmail(email: String): User? {
        return users.find { it.email == email }
    }
    
    /**
     * Obtiene todos los usuarios (para admin)
     */
    fun getAllUsers(): List<User> {
        return users.toList()
    }
}

/**
 * Clase de datos para representar un usuario
 */
data class User(
    val email: String,
    val name: String,
    val password: String,
    val phone: String,
    val id: String = email // Usar email como ID único
)
