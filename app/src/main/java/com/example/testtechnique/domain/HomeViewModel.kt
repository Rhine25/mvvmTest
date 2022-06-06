package com.example.testtechnique.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtechnique.app.EstablishmentModel
import com.example.testtechnique.app.UserModel
import com.example.testtechnique.data.EstablishmentsRepository
import com.example.testtechnique.data.UsersRepository
import com.example.testtechnique.util.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val establishmentsRepository: EstablishmentsRepository,
    private val usersRepository: UsersRepository
): ViewModel() {
    private val _establishmentDataState: MutableLiveData<DataState<List<EstablishmentModel>>> = MutableLiveData()
    private val _userDataState: MutableLiveData<DataState<UserModel>> = MutableLiveData()

    val establishmentDataState: LiveData<DataState<List<EstablishmentModel>>>
        get() = _establishmentDataState

    val userDataState: LiveData<DataState<UserModel>>
        get() = _userDataState

    fun setStateEvent(homeStateEvent: HomeStateEvent) {
        viewModelScope.launch {
            when (homeStateEvent) {
                is HomeStateEvent.GetEstablishmentsEvent -> {
                    establishmentsRepository.getEstablishmentsOf(homeStateEvent.owner)
                        .onEach { dataState ->
                            _establishmentDataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is HomeStateEvent.GetUserEvent -> {
                    usersRepository.getUser(homeStateEvent.user)
                        .onEach { dataState ->
                            _userDataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class HomeStateEvent {
    class GetEstablishmentsEvent(val owner: Int): HomeStateEvent()
    class GetUserEvent(val user: Int): HomeStateEvent()
}