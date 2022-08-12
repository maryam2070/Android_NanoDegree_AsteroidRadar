package com.udacity.asteroidradar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [AsteroidForDatabase::class], version = 1,  exportSchema = true)
abstract class AsteroidDatabase: RoomDatabase() {

    abstract val dao: AsteroidDao

    companion object {

        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        fun getInstance(context: Context): AsteroidDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AsteroidDatabase::class.java,
                        "asteroid_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}