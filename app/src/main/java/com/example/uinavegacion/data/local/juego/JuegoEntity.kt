package com.example.uinavegacion.data.local.juego

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.uinavegacion.data.local.categoria.CategoriaEntity
import com.example.uinavegacion.data.local.genero.GeneroEntity

@Entity(
    tableName = "juegos",
    foreignKeys = [
        ForeignKey(
            entity = CategoriaEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoriaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GeneroEntity::class,
            parentColumns = ["id"],
            childColumns = ["generoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categoriaId"]), Index(value = ["generoId"])]
)
data class JuegoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val imagenUrl: String? = null,
    val desarrollador: String,
    val fechaLanzamiento: String,
    val categoriaId: Long,
    val generoId: Long
)
