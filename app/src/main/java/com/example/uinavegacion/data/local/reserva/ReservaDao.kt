package com.example.uinavegacion.data.local.reserva

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReservaDao {
    // Insertar reserva
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(reserva: ReservaEntity): Long

    // Buscar reserva por id
    @Query("SELECT * FROM reservas WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ReservaEntity?

    // Obtener todas las reservas
    @Query("SELECT * FROM reservas")
    suspend fun getAll(): List<ReservaEntity>

    // Obtener reservas de un usuario
    @Query("SELECT * FROM reservas WHERE userId = :userId")
    suspend fun getByUserId(userId: Long): List<ReservaEntity>

    // Obtener reservas de un juego
    @Query("SELECT * FROM reservas WHERE juegoId = :juegoId")
    suspend fun getByJuegoId(juegoId: Long): List<ReservaEntity>

    // Obtener reservas por estado
    @Query("SELECT * FROM reservas WHERE estadoId = :estadoId")
    suspend fun getByEstadoId(estadoId: Long): List<ReservaEntity>

    // Obtener reservas activas de un usuario
    @Query("SELECT * FROM reservas WHERE userId = :userId AND estadoId = :estadoId")
    suspend fun getActivasByUserId(userId: Long, estadoId: Long): List<ReservaEntity>

    // Obtener reservas ordenadas por fecha
    @Query("SELECT * FROM reservas ORDER BY fechaReserva DESC")
    suspend fun getAllOrderedByFecha(): List<ReservaEntity>

    // Contar reservas
    @Query("SELECT COUNT(*) FROM reservas")
    suspend fun count(): Int

    // Verificar si usuario tiene reserva activa de un juego
    @Query("SELECT COUNT(*) FROM reservas WHERE userId = :userId AND juegoId = :juegoId AND estadoId = :estadoId")
    suspend fun hasReservaActiva(userId: Long, juegoId: Long, estadoId: Long): Int

    // Actualizar reserva
    @Query("UPDATE reservas SET fechaReserva = :fechaReserva, fechaVencimiento = :fechaVencimiento, estadoId = :estadoId, cantidad = :cantidad WHERE id = :id")
    suspend fun update(id: Long, fechaReserva: String, fechaVencimiento: String, estadoId: Long, cantidad: Int)

    // Actualizar solo el estado
    @Query("UPDATE reservas SET estadoId = :estadoId WHERE id = :id")
    suspend fun updateEstado(id: Long, estadoId: Long)

    // Eliminar reserva
    @Query("DELETE FROM reservas WHERE id = :id")
    suspend fun delete(id: Long)
}
