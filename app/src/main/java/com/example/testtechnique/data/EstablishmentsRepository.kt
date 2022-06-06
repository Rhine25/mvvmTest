package com.example.testtechnique.data

import com.example.testtechnique.app.EstablishmentMapper
import com.example.testtechnique.app.EstablishmentModel
import com.example.testtechnique.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EstablishmentsRepository(
    private val establishmentsLocalDataSource: EstablishmentsLocalDataSource,
    private val establishmentMapper: EstablishmentMapper
) {
    suspend fun getEstablishmentsOf(owner: Int): Flow<DataState<List<EstablishmentModel>>> = flow {
        try {
            val dbEstablishments = establishmentsLocalDataSource.getEstablishmentsOf(owner)
            emit(DataState.Success(establishmentMapper.mapFromEntityList(dbEstablishments)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}