package com.example.uinavegacion.data.local.reserva

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.uinavegacion.data.local.user.UserEntity
import com.example.uinavegacion.data.local.juego.JuegoEntity
import com.example.uinavegacion.data.local.estado.EstadoEntity

@Entity(
    tableName = "reservas",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = JuegoEntity::class,
            parentColumns = ["id"],
            childColumns = ["juegoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EstadoEntity::class,
            parentColumns = ["id"],
            childColumns = ["estadoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"]), Index(value = ["juegoId"]), Index(value = ["estadoId"])]
)
data class ReservaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val userId: Long,
    val juegoId: Long,
    val fechaReserva: String,
    val fechaVencimiento: String,
    val estadoId: Long,
    val cantidad: Int = 1
)
