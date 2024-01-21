package com.example.core_domain.repositories

import com.example.core_domain.models.CameraModel

interface ICamerasRepository {
    suspend fun isEmpty(): Boolean
    suspend fun update(): Boolean
    suspend fun getCameras(): List<CameraModel>
    suspend fun toggleFavorite(cameraId: Int)
}