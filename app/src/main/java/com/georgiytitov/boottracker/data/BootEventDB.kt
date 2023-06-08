package com.georgiytitov.boottracker.data

import android.content.Context
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BootEvent::class], version = 1)
abstract class BootEventDB : RoomDatabase() {
    abstract fun getBootEventDao(): BootEventDao

    companion object {

        @Volatile
        private var instance: BootEventDB? = null

        fun getInstance(context: Context): BootEventDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, BootEventDB::class.java, "boot-event-db")
                .build()
    }
}