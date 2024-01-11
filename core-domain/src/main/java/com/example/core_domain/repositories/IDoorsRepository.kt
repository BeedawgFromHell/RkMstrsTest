package com.example.core_domain.repositories

import androidx.lifecycle.LiveData
import com.example.core_domain.models.DoorModel

interface IDoorsRepository {
    suspend fun getAllDoors(): List<DoorModel>
    suspend fun getAllDoorsLive(): LiveData<List<DoorModel>>
    suspend fun renameDoor(doorId: Int, newName: String)
    suspend fun setIsFavorite(doorId: Int, value: Boolean)
}