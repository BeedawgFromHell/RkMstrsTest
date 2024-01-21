package com.example.core_data.repositories

import com.example.core_data.datasources.db.ILocalDatasource
import com.example.core_data.datasources.db.entities.CameraObject
import com.example.core_data.datasources.network.INetworkDatasource
import com.example.core_data.datasources.network.response_models.CameraResponseModel
import com.example.core_domain.models.CameraModel
import com.example.core_domain.repositories.ICamerasRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CamerasRepository @Inject constructor(
    private val localDatasource: ILocalDatasource,
    private val networkDatasource: INetworkDatasource
) : ICamerasRepository {

    override suspend fun isEmpty(): Boolean {
        return localDatasource.camerasIsEmpty()
    }

    override suspend fun update(): Boolean {
        return withContext(Dispatchers.IO) {
            val response = networkDatasource.getCameras() ?: return@withContext false
            val cameraObjects = response.cameras.map(CameraResponseModel::toModel).map {
                CameraObject(it)
            }

            localDatasource.insertCameras(cameraObjects)

            return@withContext true
        }
    }

    override suspend fun getCameras(): List<CameraModel> {
        return withContext(Dispatchers.IO) {
            return@withContext localDatasource.getCameras().map(CameraObject::toModel)
        }
    }

    override suspend fun toggleFavorite(cameraId: Int) {
        localDatasource.getCameras().find {
            it.id == cameraId
        }?.let { cameraObject->
            localDatasource.update(cameraObject.apply {
                favorites = !favorites
            })
        }
    }
}