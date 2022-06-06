package com.example.testtechnique.app

import com.example.testtechnique.data.db.User
import com.example.testtechnique.util.EntityMapper

class UserMapper: EntityMapper<User, UserModel> {
    override fun mapFromEntity(entity: User): UserModel {
        return UserModel(
            entity.uid,
            entity.email,
            entity.pseudo
        )
    }
}