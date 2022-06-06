package com.example.testtechnique.data

import com.example.testtechnique.data.db.Establishment
import com.example.testtechnique.data.db.EstablishmentDao

class EstablishmentsLocalDataSource(
    private val establishmentDao: EstablishmentDao
) {
    suspend fun getEstablishmentsOf(owner: Int): List<Establishment> {
        return establishmentDao.findByOwner(owner)
    }
}