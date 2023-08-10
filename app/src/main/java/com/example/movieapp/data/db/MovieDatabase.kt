package com.example.movieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomOpenHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movieapp.data.model.Movie

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase(){
//    abstract fun getMovieDao(): MovieDao
        abstract fun getMovieDao(): MovieDao

//    companion object {
//        @Volatile
//        private var INSTANCE: MovieDatabase? = null
//
//        fun getInstance(context: Context): MovieDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    MovieDatabase::class.java,
//                    "movie_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }




//    public abstract override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper

}