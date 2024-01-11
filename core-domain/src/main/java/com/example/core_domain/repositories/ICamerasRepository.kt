package com.example.core_domain.repositories

import androidx.lifecycle.LiveData
import com.example.core_domain.models.CameraModel
import com.example.core_domain.models.RoomModel

interface ICamerasRepository {
    suspend fun getAllRooms(): List<RoomModel>
    suspend fun getAllCameras(): List<CameraModel>
    suspend fun getAllCamerasLive(): LiveData<List<CameraModel>>
    suspend fun setIsFavorite(doorId: Int, value: Boolean)
}