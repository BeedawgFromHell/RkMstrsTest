package com.example.core_domain.repositories

import com.example.core_domain.models.CameraModel
import kotlinx.coroutines.flow.Flow

interface ICamerasRepository {
    suspend fun isEmpty(): Boolean
    suspend fun update()
    suspend fun getCameras(): List<CameraModel>
    suspend fun getCamerasLive(): Flow<List<CameraModel>>
    suspend fun setIsFavorite(camerasModel: CameraModel, value: Boolean)
}