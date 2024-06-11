package com.example.parkingreservation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.parkingreservation.dao.LocalReservationDao
import com.example.parkingreservation.data.entities.ReservationLocal

@Database(entities = [ReservationLocal::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reservationDao(): LocalReservationDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "reservation_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
