package com.example.testtechnique.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class User (
    //todo email et pseudo uniques
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "pseudo") val pseudo: String,
    @ColumnInfo(name = "password") val password: String
)