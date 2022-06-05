package com.example.testtechnique.data

import android.widget.Toast
import com.example.testtechnique.app.UserMapper
import com.example.testtechnique.app.UserModel
import com.example.testtechnique.util.DataState

class UsersRepository(
    private val usersLocalDataSource: UsersLocalDataSource,
    private val userMapper: UserMapper
) {
    fun getUser(email: String, password: String): DataState<UserModel> {
        try {
            val dbUser = usersLocalDataSource.getUser(email, password)
            return DataState.Success(userMapper.mapFromEntity(dbUser))
        } catch (e: Exception) {
            return DataState.Error(e)
        }
    }
}