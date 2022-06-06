package com.example.testtechnique.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtechnique.app.UserModel
import com.example.testtechnique.data.UsersRepository
import com.example.testtechnique.util.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel(
    private val usersRepository: UsersRepository
): ViewModel() {
    private val _dataState: MutableLiveData<DataState<UserModel>> = MutableLiveData()

    val dataState: LiveData<DataState<UserModel>>
        get() = _dataState

    fun setStateEvent(loginStateEvent: LoginStateEvent) {
        viewModelScope.launch {
            when (loginStateEvent) {
                is LoginStateEvent.GetLoginEvent -> {
                    usersRepository.getUser(loginStateEvent.email, loginStateEvent.password)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class LoginStateEvent {
    class GetLoginEvent(val email: String, val password: String): LoginStateEvent()
}