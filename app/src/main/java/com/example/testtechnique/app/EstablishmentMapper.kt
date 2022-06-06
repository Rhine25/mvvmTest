package com.example.testtechnique.app

import com.example.testtechnique.data.db.Establishment
import com.example.testtechnique.util.EntityMapper

class EstablishmentMapper: EntityMapper<Establishment, EstablishmentModel> {
    override fun mapFromEntity(entity: Establishment): EstablishmentModel {
        return EstablishmentModel(
            entity.uid,
            entity.name,
            entity.address,
            entity.city,
            entity.owner
        )
    }

    fun mapFromEntityList(entities: List<Establishment>): List<EstablishmentModel> {
        return entities.map { mapFromEntity(it) }
    }
}