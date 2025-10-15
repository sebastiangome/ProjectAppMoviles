package com.example.uinavegacion.data.local.estado

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estados")
data class EstadoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nombre: String,
    val descripcion: String
)
