package com.example.testtechnique.data

import com.example.testtechnique.data.db.User
import com.example.testtechnique.data.db.UserDao

class UsersLocalDataSource(
    private val userDao: UserDao
) {
    fun getUser(email: String, password: String): User {
        return userDao.findByLogins(email, password)
    }
}