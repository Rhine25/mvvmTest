package com.example.testtechnique.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Establishment::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun establishmentDao(): EstablishmentDao
}