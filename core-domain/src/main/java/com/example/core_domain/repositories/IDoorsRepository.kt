package com.example.core_domain.repositories

import com.example.core_domain.models.DoorModel

interface IDoorsRepository {
    suspend fun isEmpty(): Boolean
    suspend fun update(): Boolean
    suspend fun getAllDoors(): List<DoorModel>
    suspend fun renameDoor(door: DoorModel, newName: String)
    suspend fun toggleFavorite(doorId: Int)
}