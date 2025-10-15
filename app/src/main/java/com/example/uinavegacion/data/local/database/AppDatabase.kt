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

//registrar las entidades pertenecientes a la BD

@Database(

    entities = [UserEntity::class],

    version = 1,

    exportSchema = true

)

abstract class AppDatabase: RoomDatabase(){

    //exponemos o incluimos los DAO de cada entidad

    abstract fun userDao(): UserDao



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
