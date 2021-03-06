package com.example.testtechnique.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EstablishmentDao {
    @Query("SELECT * FROM establishment WHERE owner LIKE :owner")
    suspend fun findByOwner(owner: Int): List<Establishment>

    @Insert
    suspend fun insertEstablishment(establishment: Establishment)
}