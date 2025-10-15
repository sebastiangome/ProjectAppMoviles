package com.example.uinavegacion.data.local.detalle

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DetalleDao {
    // Insertar detalle de orden
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(detalle: DetalleEntity): Long

    // Buscar detalle por id
    @Query("SELECT * FROM detalles_orden WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): DetalleEntity?

    // Obtener todos los detalles
    @Query("SELECT * FROM detalles_orden")
    suspend fun getAll(): List<DetalleEntity>

    // Obtener detalles de una orden
    @Query("SELECT * FROM detalles_orden WHERE ordenId = :ordenId")
    suspend fun getByOrdenId(ordenId: Long): List<DetalleEntity>

    // Obtener detalles de un juego
    @Query("SELECT * FROM detalles_orden WHERE juegoId = :juegoId")
    suspend fun getByJuegoId(juegoId: Long): List<DetalleEntity>

    // Contar detalles
    @Query("SELECT COUNT(*) FROM detalles_orden")
    suspend fun count(): Int

    // Calcular total de una orden
    @Query("SELECT SUM(subtotal) FROM detalles_orden WHERE ordenId = :ordenId")
    suspend fun getTotalOrden(ordenId: Long): Double?

    // Actualizar detalle
    @Query("UPDATE detalles_orden SET cantidad = :cantidad, precioUnitario = :precio, subtotal = :subtotal WHERE id = :id")
    suspend fun update(id: Long, cantidad: Int, precio: Double, subtotal: Double)

    // Eliminar detalle
    @Query("DELETE FROM detalles_orden WHERE id = :id")
    suspend fun delete(id: Long)

    // Eliminar todos los detalles de una orden
    @Query("DELETE FROM detalles_orden WHERE ordenId = :ordenId")
    suspend fun deleteByOrdenId(ordenId: Long)
}
