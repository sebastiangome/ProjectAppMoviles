package com.example.uinavegacion.data.local.licencia

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LicenciaDao {
    // Insertar licencia
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(licencia: LicenciaEntity): Long

    // Buscar licencia por id
    @Query("SELECT * FROM licencias WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): LicenciaEntity?

    // Buscar licencia por código
    @Query("SELECT * FROM licencias WHERE codigoLicencia = :codigo LIMIT 1")
    suspend fun getByCodigo(codigo: String): LicenciaEntity?

    // Obtener todas las licencias
    @Query("SELECT * FROM licencias")
    suspend fun getAll(): List<LicenciaEntity>

    // Obtener licencias de un usuario
    @Query("SELECT * FROM licencias WHERE userId = :userId")
    suspend fun getByUserId(userId: Long): List<LicenciaEntity>

    // Obtener licencias activas de un usuario
    @Query("SELECT * FROM licencias WHERE userId = :userId AND activa = 1")
    suspend fun getActivasByUserId(userId: Long): List<LicenciaEntity>

    // Obtener licencias de un juego
    @Query("SELECT * FROM licencias WHERE juegoId = :juegoId")
    suspend fun getByJuegoId(juegoId: Long): List<LicenciaEntity>

    // Contar licencias
    @Query("SELECT COUNT(*) FROM licencias")
    suspend fun count(): Int

    // Verificar si usuario tiene licencia de un juego
    @Query("SELECT COUNT(*) FROM licencias WHERE userId = :userId AND juegoId = :juegoId AND activa = 1")
    suspend fun hasLicencia(userId: Long, juegoId: Long): Int

    // Actualizar licencia
    @Query("UPDATE licencias SET codigoLicencia = :codigo, fechaCompra = :fechaCompra, fechaExpiracion = :fechaExpiracion, activa = :activa WHERE id = :id")
    suspend fun update(id: Long, codigo: String, fechaCompra: String, fechaExpiracion: String?, activa: Boolean)

    // Desactivar licencia
    @Query("UPDATE licencias SET activa = 0 WHERE id = :id")
    suspend fun desactivar(id: Long)

    // Eliminar licencia
    @Query("DELETE FROM licencias WHERE id = :id")
    suspend fun delete(id: Long)
}
