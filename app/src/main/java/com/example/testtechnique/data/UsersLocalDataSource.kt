package com.example.testtechnique.data

import com.example.testtechnique.data.db.User
import com.example.testtechnique.data.db.UserDao

class UsersLocalDataSource(
    private val userDao: UserDao
) {
    suspend fun getUser(email: String, password: String): User {
        return userDao.findByLogins(email, password)
    }

    suspend fun getUser(id: Int): User {
        return userDao.findById(id)
    }
}