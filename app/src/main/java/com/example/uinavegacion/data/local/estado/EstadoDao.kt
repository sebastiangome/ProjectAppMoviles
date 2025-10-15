package com.example.uinavegacion.data.local.estado

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EstadoDao {
    // Insertar estado
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(estado: EstadoEntity): Long

    // Buscar estado por id
    @Query("SELECT * FROM estados WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): EstadoEntity?

    // Buscar estado por nombre
    @Query("SELECT * FROM estados WHERE nombre = :nombre LIMIT 1")
    suspend fun getByNombre(nombre: String): EstadoEntity?

    // Obtener todos los estados
    @Query("SELECT * FROM estados")
    suspend fun getAll(): List<EstadoEntity>

    // Contar estados
    @Query("SELECT COUNT(*) FROM estados")
    suspend fun count(): Int

    // Obtener estados ordenados por nombre
    @Query("SELECT * FROM estados ORDER BY nombre ASC")
    suspend fun getAllOrderedByNombre(): List<EstadoEntity>

    // Actualizar estado
    @Query("UPDATE estados SET nombre = :nombre, descripcion = :descripcion WHERE id = :id")
    suspend fun update(id: Long, nombre: String, descripcion: String)

    // Eliminar estado
    @Query("DELETE FROM estados WHERE id = :id")
    suspend fun delete(id: Long)
}
