package com.example.core_domain.repositories

import androidx.lifecycle.LiveData
import com.example.core_domain.models.DoorModel
import kotlinx.coroutines.flow.Flow

interface IDoorsRepository {
    suspend fun isEmpty(): Boolean
    suspend fun update()
    suspend fun getAllDoors(): List<DoorModel>
    suspend fun getAllDoorsLive(): Flow<List<DoorModel>>
    suspend fun renameDoor(door: DoorModel, newName: String)
    suspend fun setIsFavorite(door: DoorModel, value: Boolean)
}