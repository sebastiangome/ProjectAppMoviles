package com.example.uinavegacion.data.local.rol

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RolDao {
    // Insertar rol
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(rol: RolEntity): Long

    // Buscar rol por id
    @Query("SELECT * FROM roles WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): RolEntity?

    // Buscar rol por nombre
    @Query("SELECT * FROM roles WHERE nombre = :nombre LIMIT 1")
    suspend fun getByNombre(nombre: String): RolEntity?

    // Obtener todos los roles
    @Query("SELECT * FROM roles")
    suspend fun getAll(): List<RolEntity>

    // Contar roles
    @Query("SELECT COUNT(*) FROM roles")
    suspend fun count(): Int

    // Obtener roles ordenados por nombre
    @Query("SELECT * FROM roles ORDER BY nombre ASC")
    suspend fun getAllOrderedByNombre(): List<RolEntity>

    // Actualizar rol
    @Query("UPDATE roles SET nombre = :nombre, descripcion = :descripcion WHERE id = :id")
    suspend fun update(id: Long, nombre: String, descripcion: String)

    // Eliminar rol
    @Query("DELETE FROM roles WHERE id = :id")
    suspend fun delete(id: Long)
}
