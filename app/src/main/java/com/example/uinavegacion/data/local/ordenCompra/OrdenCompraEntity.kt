package com.example.uinavegacion.data.local.ordenCompra

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.uinavegacion.data.local.user.UserEntity
import com.example.uinavegacion.data.local.estado.EstadoEntity

@Entity(
    tableName = "ordenes_compra",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EstadoEntity::class,
            parentColumns = ["id"],
            childColumns = ["estadoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"]), Index(value = ["estadoId"])]
)
data class OrdenCompraEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val userId: Long,
    val fechaOrden: String,
    val total: Double,
    val estadoId: Long,
    val metodoPago: String,
    val direccionEnvio: String? = null
)
