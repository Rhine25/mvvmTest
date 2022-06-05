package com.example.testtechnique.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE pseudo LIKE :pseudo LIMIT 1")
    suspend fun findByPseudo(pseudo: String): User

    @Query("SELECT * FROM user WHERE email LIKE :email AND password LIKE :password LIMIT 1")
    suspend fun findByLogins(email: String, password: String): User

    @Insert
    suspend fun insertUser(user: User)
}