package com.example.uinavegacion.data.local.detalle

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.uinavegacion.data.local.ordenCompra.OrdenCompraEntity
import com.example.uinavegacion.data.local.juego.JuegoEntity

@Entity(
    tableName = "detalles_orden",
    foreignKeys = [
        ForeignKey(
            entity = OrdenCompraEntity::class,
            parentColumns = ["id"],
            childColumns = ["ordenId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = JuegoEntity::class,
            parentColumns = ["id"],
            childColumns = ["juegoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["ordenId"]), Index(value = ["juegoId"])]
)
data class DetalleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val ordenId: Long,
    val juegoId: Long,
    val cantidad: Int,
    val precioUnitario: Double,
    val subtotal: Double
)
