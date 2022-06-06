package com.example.testtechnique.util

interface EntityMapper<Entity, Model> {
    fun mapFromEntity(entity: Entity): Model
}