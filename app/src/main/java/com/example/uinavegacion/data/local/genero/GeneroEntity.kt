package com.example.uinavegacion.data.local.genero

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "generos")
data class GeneroEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nombre: String,
    val descripcion: String
)
