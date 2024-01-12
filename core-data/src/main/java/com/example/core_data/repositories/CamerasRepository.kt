package com.example.core_data.repositories

import com.example.core_data.db.entities.CameraObject
import com.example.core_data.network.HttpClient
import com.example.core_data.network.response_models.CameraResponseModel
import com.example.core_domain.models.CameraModel
import com.example.core_domain.repositories.ICamerasRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CamerasRepository @Inject constructor(
    private val realm: Realm,
    private val http: HttpClient
): ICamerasRepository {
    override suspend fun isEmpty(): Boolean {
        return realm.query<CameraObject>().find().isEmpty()
    }

    override suspend fun update(): Boolean {
        val response = http.getCameras() ?: return false
        val cameraModels = response.cameras.map(CameraResponseModel::toModel)

        realm.writeBlocking {
            cameraModels.map {
                CameraObject(it)
            }.forEach {
                this.copyToRealm(it, UpdatePolicy.ALL)
            }
        }

        return true
    }

    override suspend fun getCameras(): List<CameraModel> {
        return realm.query<CameraObject>().find().map(CameraObject::toModel)
    }

    override fun getCamerasLive(): Flow<List<CameraModel>> {
        return realm.query<CameraObject>().find().asFlow().map {
            it.list.map(CameraObject::toModel)
        }
    }

    override suspend fun setIsFavorite(camerasModel: CameraModel, value: Boolean) {
        realm.writeBlocking {
            findLatest(CameraObject(camerasModel))?.favorites = value
        }
    }
}