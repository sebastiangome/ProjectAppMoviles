package com.example.uinavegacion.data.local.database

import androidx.room.Database
import com.example.uinavegacion.data.local.user.UserEntity
import androidx.room.RoomDatabase
import com.example.uinavegacion.data.local.user.UserDao
import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.uinavegacion.data.local.categoria.CategoriaEntity
import com.example.uinavegacion.data.local.categoria.CategoriaDao
import com.example.uinavegacion.data.local.genero.GeneroEntity
import com.example.uinavegacion.data.local.genero.GeneroDao
import com.example.uinavegacion.data.local.juego.JuegoEntity
import com.example.uinavegacion.data.local.juego.JuegoDao
import com.example.uinavegacion.data.local.estado.EstadoEntity
import com.example.uinavegacion.data.local.estado.EstadoDao
import com.example.uinavegacion.data.local.rol.RolEntity
import com.example.uinavegacion.data.local.rol.RolDao
import com.example.uinavegacion.data.local.licencia.LicenciaEntity
import com.example.uinavegacion.data.local.licencia.LicenciaDao
import com.example.uinavegacion.data.local.ordenCompra.OrdenCompraEntity
import com.example.uinavegacion.data.local.ordenCompra.OrdenCompraDao
import com.example.uinavegacion.data.local.detalle.DetalleEntity
import com.example.uinavegacion.data.local.detalle.DetalleDao
import com.example.uinavegacion.data.local.reserva.ReservaEntity
import com.example.uinavegacion.data.local.reserva.ReservaDao

//registrar las entidades pertenecientes a la BD

@Database(

    entities = [
        UserEntity::class,
        CategoriaEntity::class,
        GeneroEntity::class,
        JuegoEntity::class,
        EstadoEntity::class,
        RolEntity::class,
        LicenciaEntity::class,
        OrdenCompraEntity::class,
        DetalleEntity::class,
        ReservaEntity::class
    ],

    version = 1,

    exportSchema = false

)

abstract class AppDatabase: RoomDatabase(){

    //exponemos o incluimos los DAO de cada entidad

    abstract fun userDao(): UserDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun generoDao(): GeneroDao
    abstract fun juegoDao(): JuegoDao
    abstract fun estadoDao(): EstadoDao
    abstract fun rolDao(): RolDao
    abstract fun licenciaDao(): LicenciaDao
    abstract fun ordenCompraDao(): OrdenCompraDao
    abstract fun detalleDao(): DetalleDao
    abstract fun reservaDao(): ReservaDao



    companion object {

        //variable de instanciacion para la base de datos

        @Volatile

        private var INSTANCE: AppDatabase? = null



        //variable para el nombre del archivo para la BD

        private const val DB_NAME = "ui_navegacion.db"



        //creamos la instancia unica de la BD

        fun getInstance(context: Context): AppDatabase{

            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(

                    context.applicationContext,

                    AppDatabase::class.java,

                    DB_NAME

                )

                    //ejecute cuando la BD se crea por primera vez

                    .addCallback(object : RoomDatabase.Callback(){

                        override fun onCreate(db: SupportSQLiteDatabase) {

                            super.onCreate(db)

                            //lanzamos una corrutina para insertar los datos iniciales

                            CoroutineScope(Dispatchers.IO).launch {

                                val dao = getInstance(context).userDao()

                                //precargamos usuarios

                                val seed = listOf(

                                    UserEntity(

                                        name = "Admin",

                                        email = "a@a.cl",

                                        phone = "12345678",

                                        password = "Admin123!"

                                    ),

                                    UserEntity(

                                        name = "Jose",

                                        email = "b@b.cl",

                                        phone = "12345678",

                                        password = "Jose123!"

                                    )

                                )

                                //insertar solo si la tabla esta vacia

                                if(dao.count() == 0){

                                    seed.forEach { dao.insert(it) }

                                }

                            }

                        }

                    })

                    .fallbackToDestructiveMigration()

                    .build()

                INSTANCE = instance

                instance

            }

        }

    }

}
