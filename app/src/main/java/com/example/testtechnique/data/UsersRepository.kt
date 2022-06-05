package com.example.testtechnique.data

import android.util.Log
import com.example.testtechnique.app.UserMapper
import com.example.testtechnique.app.UserModel
import com.example.testtechnique.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UsersRepository(
    private val usersLocalDataSource: UsersLocalDataSource,
    private val userMapper: UserMapper
) {
    suspend fun getUser(email: String, password: String): Flow<DataState<UserModel>> = flow {
        emit(DataState.Loading)
        try {
            val dbUser = usersLocalDataSource.getUser(email, password)
            emit(DataState.Success(userMapper.mapFromEntity(dbUser)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}